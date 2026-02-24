import { BookOpen, TrendingUp, Zap, Package } from 'lucide-react';

export function Quests() {
  return (
    <div className="p-4 pb-24 space-y-6 bg-slate-50 min-h-full">
      <h1 className="text-2xl font-bold text-slate-900">Quests</h1>
      <p className="text-slate-500 -mt-4">Earn rewards and master the market</p>

      {/* Tabs */}
      <div className="flex p-1 bg-slate-200 rounded-xl">
        <button className="flex-1 py-2 px-4 bg-white rounded-lg shadow-sm text-slate-900 font-bold text-sm">
          Daily
        </button>
        <button className="flex-1 py-2 px-4 text-slate-500 font-bold text-sm hover:text-slate-700">
          Monthly
        </button>
        <button className="flex-1 py-2 px-4 text-slate-500 font-bold text-sm hover:text-slate-700">
          Badges
        </button>
      </div>

      {/* Quest List */}
      <div className="space-y-4">
        {/* Quest 1 */}
        <div className="bg-white p-4 rounded-2xl border-2 border-slate-100 shadow-sm flex items-center gap-4">
          <div className="w-16 h-16 bg-slate-100 rounded-xl flex items-center justify-center shrink-0">
            <BookOpen className="text-slate-500 w-8 h-8" />
          </div>
          <div className="flex-1">
            <div className="flex justify-between items-center mb-1">
              <h3 className="font-bold text-slate-900">Complete 2 lessons</h3>
              <div className="flex items-center gap-1 text-yellow-500 font-bold text-sm">
                <span>💎</span>
                <span>20</span>
              </div>
            </div>
            <p className="text-slate-400 text-xs mb-2">1 / 2 completed</p>
            <div className="h-3 bg-slate-100 rounded-full overflow-hidden">
              <div className="h-full bg-slate-500 w-1/2 rounded-full"></div>
            </div>
          </div>
        </div>

        {/* Quest 2 */}
        <div className="bg-white p-4 rounded-2xl border-2 border-slate-100 shadow-sm flex items-center gap-4">
          <div className="w-16 h-16 bg-green-50 rounded-xl flex items-center justify-center shrink-0">
            <TrendingUp className="text-green-500 w-8 h-8" />
          </div>
          <div className="flex-1">
            <div className="flex justify-between items-center mb-1">
              <h3 className="font-bold text-slate-900">Read Bulls vs Bears</h3>
              <div className="flex items-center gap-1 text-yellow-500 font-bold text-sm">
                <span>💎</span>
                <span>15</span>
              </div>
            </div>
            <p className="text-slate-400 text-xs mb-2">Not started</p>
            <div className="h-3 bg-slate-100 rounded-full overflow-hidden">
              <div className="h-full bg-slate-500 w-0 rounded-full"></div>
            </div>
          </div>
        </div>

        {/* Quest 3 */}
        <div className="bg-white p-4 rounded-2xl border-2 border-slate-100 shadow-sm flex items-center gap-4 opacity-60">
          <div className="w-16 h-16 bg-blue-50 rounded-xl flex items-center justify-center shrink-0">
            <Zap className="text-blue-400 w-8 h-8 fill-current" />
          </div>
          <div className="flex-1">
            <div className="flex justify-between items-center mb-1">
              <h3 className="font-bold text-slate-900">Perfect Lesson</h3>
              <div className="bg-slate-800 text-white text-[10px] font-bold px-2 py-0.5 rounded uppercase">
                Completed
              </div>
            </div>
            <p className="text-slate-400 text-xs mb-2">1 / 1 completed</p>
            <div className="h-3 bg-slate-100 rounded-full overflow-hidden">
              <div className="h-full bg-slate-500 w-full rounded-full"></div>
            </div>
          </div>
        </div>
      </div>

      {/* Weekly Chest */}
      <div className="bg-gradient-to-br from-blue-600 to-blue-800 rounded-2xl p-6 text-white shadow-lg relative overflow-hidden">
        <div className="relative z-10">
          <div className="flex justify-between items-start mb-4">
            <div>
              <h3 className="font-bold text-lg italic">WEEKLY CHEST</h3>
              <p className="text-blue-100 text-sm">Finish 10 daily quests to unlock</p>
            </div>
            <Package className="w-10 h-10 text-white opacity-90" />
          </div>
          
          <div className="flex justify-between items-end mb-2">
            <span className="font-bold text-xs uppercase tracking-wider opacity-80">Progress</span>
            <span className="font-bold text-sm">7/10</span>
          </div>
          
          <div className="h-4 bg-black/20 rounded-full overflow-hidden border border-white/10">
            <div className="h-full bg-white w-[70%] rounded-full shadow-[0_0_10px_rgba(255,255,255,0.5)]"></div>
          </div>
        </div>
      </div>
    </div>
  );
}
