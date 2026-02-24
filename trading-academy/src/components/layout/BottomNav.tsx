import { Link, useLocation } from 'react-router-dom';
import { Home, Trophy, CheckCircle, User } from 'lucide-react';
import { cn } from '@/lib/utils';

export function BottomNav() {
  const location = useLocation();

  const navItems = [
    { icon: Home, label: 'Learn', path: '/' },
    { icon: Trophy, label: 'Rank', path: '/leaderboard' },
    { icon: CheckCircle, label: 'Quests', path: '/quests' },
    { icon: User, label: 'Profile', path: '/profile' },
  ];

  return (
    <nav className="fixed bottom-0 left-0 right-0 bg-white border-t border-slate-200 pb-6 pt-2 px-4 z-50">
      <div className="flex justify-around items-end max-w-md mx-auto">
        {navItems.map((item) => {
          const isActive = location.pathname === item.path;
          return (
            <Link
              key={item.path}
              to={item.path}
              className="flex flex-1 flex-col items-center justify-center gap-1 group cursor-pointer"
            >
              <div
                className={cn(
                  "flex h-10 w-16 items-center justify-center rounded-full transition-all",
                  isActive ? "bg-blue-100" : "bg-transparent group-hover:bg-slate-100"
                )}
              >
                <item.icon
                  className={cn(
                    "w-7 h-7 transition-colors",
                    isActive ? "text-blue-500 fill-current" : "text-slate-400 group-hover:text-slate-600"
                  )}
                  strokeWidth={isActive ? 2.5 : 2}
                />
              </div>
              <p
                className={cn(
                  "text-xs font-bold tracking-wide transition-colors",
                  isActive ? "text-blue-500" : "text-slate-400 group-hover:text-slate-600"
                )}
              >
                {item.label}
              </p>
            </Link>
          );
        })}
      </div>
    </nav>
  );
}
