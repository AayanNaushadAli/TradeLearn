-- 1. Create a function that makes a new profile row
create or replace function public.handle_new_user()
returns trigger as $$
begin
  insert into public.user_profiles (id, username, total_xp, current_streak, hearts)
  values (
    new.id, 
    coalesce(new.raw_user_meta_data->>'full_name', 'Trader'), 
    0, 
    0, 
    5
  );
  return new;
end;
$$ language plpgsql security definer;

-- 2. Attach the trigger to the Auth table
create trigger on_auth_user_created
  after insert on auth.users
  for each row execute procedure public.handle_new_user();