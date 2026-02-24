import { Outlet } from 'react-router-dom';
import { TopBar } from './TopBar';
import { BottomNav } from './BottomNav';

export function Layout() {
  return (
    <div className="relative flex h-full min-h-screen w-full flex-col max-w-md mx-auto shadow-2xl overflow-hidden bg-slate-50">
      <TopBar />
      <div className="flex-1 overflow-y-auto pb-24 relative no-scrollbar">
        <Outlet />
      </div>
      <BottomNav />
    </div>
  );
}
