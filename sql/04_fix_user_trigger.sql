-- Fix for: Unique constraint violation on user_profiles.username when multiple users sign up without a name
-- This trigger properly generates a unique username for each new user.

create or replace function public.handle_new_user()
returns trigger as $$
declare
  base_username text;
  final_username text;
begin
  -- Try to get the username from metadata, fallback to 'Trader'
  base_username := coalesce(new.raw_user_meta_data->>'username', new.raw_user_meta_data->>'full_name', 'Trader');
  final_username := base_username;
  
  -- If 'Trader' or if the exact username is taken, append a random string
  if exists (select 1 from public.user_profiles where username = final_username) then
    final_username := base_username || '_' || substr(md5(random()::text), 1, 6);
  end if;

  insert into public.user_profiles (id, username, total_xp, current_streak, hearts)
  values (
    new.id, 
    final_username, 
    0, 
    0, 
    5
  );
  return new;
end;
$$ language plpgsql security definer;

-- Ensure the trigger is attached
drop trigger if exists on_auth_user_created on auth.users;
create trigger on_auth_user_created
  after insert on auth.users
  for each row execute procedure public.handle_new_user();
