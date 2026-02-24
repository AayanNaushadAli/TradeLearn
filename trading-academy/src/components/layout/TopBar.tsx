import { Flame, Gem, Heart } from 'lucide-react';
import { useStore } from '@/store/useStore';

export function TopBar() {
  const { streak, gems, hearts } = useStore();

  return (
    <div className="sticky top-0 z-50 flex items-center justify-between px-4 py-3 bg-white/90 backdrop-blur-md border-b border-slate-200 max-w-md mx-auto w-full">
      {/* Course Selector (Simulated) */}
      <div className="flex items-center gap-2">
        <div className="w-8 h-6 rounded overflow-hidden relative shadow-sm bg-gradient-to-tr from-blue-600 to-cyan-400">
          {/* Flag content */}
        </div>
      </div>

      {/* Stats */}
      <div className="flex items-center gap-4">
        <div className="flex items-center gap-1.5">
          <Flame className="w-5 h-5 text-orange-500 fill-current" />
          <span className="text-orange-500 font-bold text-sm">{streak}</span>
        </div>
        <div className="flex items-center gap-1.5">
          <Gem className="w-5 h-5 text-blue-400 fill-current" />
          <span className="text-blue-400 font-bold text-sm">{gems}</span>
        </div>
        <div className="flex items-center gap-1.5">
          <Heart className="w-5 h-5 text-red-500 fill-current" />
          <span className="text-red-500 font-bold text-sm">{hearts}</span>
        </div>
      </div>
    </div>
  );
}
