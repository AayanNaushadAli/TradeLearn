import { useStore } from '@/store/useStore';
import { Settings, Share2, Zap, Flame, BookOpen, Trophy, TrendingUp, GraduationCap, Edit2 } from 'lucide-react';

export function Profile() {
  const { xp, streak, league, completedLessons } = useStore();

  return (
    <div className="bg-slate-50 min-h-full pb-24">
      {/* Header */}
      <div className="bg-white p-4 flex justify-between items-center sticky top-0 z-10 border-b border-slate-100">
        <Settings className="text-slate-400 w-6 h-6" />
        <h1 className="font-bold text-slate-900">Profile</h1>
        <Share2 className="text-slate-400 w-6 h-6" />
      </div>

      {/* Profile Card */}
      <div className="bg-white pb-8 pt-4 px-4 mb-4 border-b border-slate-100">
        <div className="flex flex-col items-center">
          <div className="relative mb-4">
            <div className="w-32 h-32 rounded-full border-4 border-slate-100 overflow-hidden">
              <img
                src="https://lh3.googleusercontent.com/aida-public/AB6AXuAxX6iQMzZIDwd2ox1QDMq1YJzB6kr93bWH7tF-KUwmtQLgkwRSdkD96LTCdeBm76q_7VnpUB3gERYOKBCABP9C8eyciOYQQdodXgI5A10_CeFDsLMzfu0mroUrD_sL_oz1DTTr4zgJanRMVxzkDKLLYqZIoyY3HXEzad9uwVF_oue27vURecxL0b88jD6sIvk4ME9dk6Ht7-Ub5fu2oZQReAFhBQv5vcnYv5U4IkuuUlpDjweVE9QxrnV-F4y4XRx5sd3D7e29iRk"
                alt="Profile"
                className="w-full h-full object-cover"
              />
            </div>
            <button className="absolute bottom-0 right-0 bg-slate-500 text-white p-2 rounded-full border-4 border-white">
              <Edit2 className="w-4 h-4" />
            </button>
          </div>
          
          <h2 className="text-2xl font-bold text-slate-900">Alex Trader</h2>
          <p className="text-slate-400 font-medium mb-2">@alextrader_123</p>
          <div className="flex items-center gap-2 text-slate-400 text-sm">
            <span>📅</span>
            <span>Joined October 2023</span>
          </div>
        </div>
      </div>

      {/* Statistics */}
      <div className="px-4 mb-6">
        <h3 className="font-bold text-slate-900 text-lg mb-4">Statistics</h3>
        <div className="grid grid-cols-2 gap-3">
          <div className="bg-white p-4 rounded-2xl border-2 border-slate-100 shadow-sm">
            <div className="flex items-center gap-2 mb-1">
              <Zap className="w-4 h-4 text-yellow-500 fill-current" />
              <span className="text-xs font-bold text-slate-400 uppercase">Total XP</span>
            </div>
            <p className="text-xl font-bold text-slate-900">{xp.toLocaleString()}</p>
          </div>
          
          <div className="bg-white p-4 rounded-2xl border-2 border-slate-100 shadow-sm">
            <div className="flex items-center gap-2 mb-1">
              <Flame className="w-4 h-4 text-orange-500 fill-current" />
              <span className="text-xs font-bold text-slate-400 uppercase">Streak</span>
            </div>
            <p className="text-xl font-bold text-slate-900">{streak} Days</p>
          </div>
          
          <div className="bg-white p-4 rounded-2xl border-2 border-slate-100 shadow-sm">
            <div className="flex items-center gap-2 mb-1">
              <BookOpen className="w-4 h-4 text-slate-500" />
              <span className="text-xs font-bold text-slate-400 uppercase">Lessons</span>
            </div>
            <p className="text-xl font-bold text-slate-900">{completedLessons}</p>
          </div>
          
          <div className="bg-white p-4 rounded-2xl border-2 border-slate-100 shadow-sm">
            <div className="flex items-center gap-2 mb-1">
              <Trophy className="w-4 h-4 text-green-500" />
              <span className="text-xs font-bold text-slate-400 uppercase">League</span>
            </div>
            <p className="text-xl font-bold text-slate-900">{league}</p>
          </div>
        </div>
      </div>

      {/* Achievements */}
      <div className="px-4">
        <div className="flex justify-between items-center mb-4">
          <h3 className="font-bold text-slate-900 text-lg">Achievements</h3>
          <button className="text-blue-500 font-bold text-sm uppercase">View All</button>
        </div>
        
        <div className="space-y-3">
          <div className="bg-white p-4 rounded-2xl border-2 border-slate-100 shadow-sm flex items-center gap-4">
            <div className="w-16 h-16 bg-slate-100 rounded-full flex items-center justify-center shrink-0">
              <TrendingUp className="w-8 h-8 text-slate-500" />
            </div>
            <div className="flex-1">
              <h4 className="font-bold text-slate-900">Bull Market</h4>
              <p className="text-slate-500 text-sm mb-2">Complete 10 profitable mock trades</p>
              <div className="h-2 bg-slate-100 rounded-full overflow-hidden">
                <div className="h-full bg-slate-500 w-[80%] rounded-full"></div>
              </div>
              <p className="text-[10px] font-bold text-slate-400 mt-1 uppercase">8/10 Completed</p>
            </div>
          </div>
          
          <div className="bg-white p-4 rounded-2xl border-2 border-slate-100 shadow-sm flex items-center gap-4">
            <div className="w-16 h-16 bg-orange-100 rounded-full flex items-center justify-center shrink-0">
              <GraduationCap className="w-8 h-8 text-orange-500" />
            </div>
            <div className="flex-1">
              <h4 className="font-bold text-slate-900">Market Scholar</h4>
              <p className="text-slate-500 text-sm mb-2">Finish the 'Technical Analysis' course</p>
              <div className="h-2 bg-slate-100 rounded-full overflow-hidden">
                <div className="h-full bg-orange-500 w-full rounded-full"></div>
              </div>
              <p className="text-[10px] font-bold text-orange-500 mt-1 uppercase">Completed</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
