import { TrendingUp, TrendingDown, Minus, Zap } from 'lucide-react';
import { cn } from '@/lib/utils';

const users = [
  {
    rank: 1,
    name: 'Sarah Mitchell',
    xp: 9820,
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBmCLelNEQMUUvKcL0jCfjrAxLFe5TEDWBhXO0qexHwYmL2AUJkkCq0ZSKBlxGqI5BCSqaMh_s4xiYI90mADj81BN5P6YLQr1DKqJWei5_cR0eSyzxQ_ar0sYBQZ_YANKcwceL5N1mFHg1y7gk-cTOMdD-ayBSFW3RHCCxnW4jfagMck8bZqTlyyE6aauVSewyQ2HMfwOGypDyB9T75GB7FRgVEEhMFvmIIbQoh2bpuWi1SrsT6nTVdvRsDzoD1ZFA7zhEISRh2AzY',
    trend: 'up',
    trendValue: '14%',
  },
  {
    rank: 2,
    name: 'David Chen',
    xp: 8440,
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBub7abbfCJ4JmjFMRSNQ-Kn5X4KeZt8c0YN8NhCwzFvBdjvda9fw-bAaWmgMmnP-4yfa_bkdsAwEDu90gdk0q1EB9Xv-Jyp1d1pjPphlU0blcoXtSOLfS267zr4aBdoOQMLbUyYVaQ0dJYC7VLoPvj0aydKoeSzWECqIAE9Xt72D7z8izDBIvr1PmYhXD3NDcR6oio7i9QHNT6AG2Zlkq1S0TXwEpkA3EfZ--WFD0VZjUfCusvRBK5M4Jav0g8g4LE3YvswdcYbl8',
    trend: 'up',
    trendValue: '8%',
  },
  {
    rank: 3,
    name: 'Leo V.',
    xp: 7210,
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuA79_7XWLJSRsJHqes8oFtKsFR_1PkyJ62Bhsh3-tkOeDw1W_I26NL-F8kA6UwagtB2_kLtIatf_iOj3cgR4ixTPP9CqZ9DjZ0vS3s5sAjVUc0EJXhnBdyG0FLKbRwdL0F5k4zeTaB8347Zn_SOG4WHT443DP2FoT3Ot9zt7iBh0J9gMuBGdWte3UK0KXmIyb_n8IjJIcxFFfYA6aNdXLPeFsXgJ2rvpWHfWKgaSvBExlwatJwBxAiDuzYzulaKWQe9j8MZaS7A0H8',
    trend: 'flat',
    trendValue: '0%',
  },
  {
    rank: 12,
    name: 'You',
    xp: 2450,
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAxX6iQMzZIDwd2ox1QDMq1YJzB6kr93bWH7tF-KUwmtQLgkwRSdkD96LTCdeBm76q_7VnpUB3gERYOKBCABP9C8eyciOYQQdodXgI5A10_CeFDsLMzfu0mroUrD_sL_oz1DTTr4zgJanRMVxzkDKLLYqZIoyY3HXEzad9uwVF_oue27vURecxL0b88jD6sIvk4ME9dk6Ht7-Ub5fu2oZQReAFhBQv5vcnYv5U4IkuuUlpDjweVE9QxrnV-F4y4XRx5sd3D7e29iRk',
    trend: 'active',
    trendValue: 'Active',
    isMe: true,
  },
  {
    rank: 13,
    name: 'Elena Rodriguez',
    xp: 2320,
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAlUx2rLMKgoR_bIiO1_bNUG--Vx1bENQ-vkB7jp6QBfmmAYcwBeCN_WFAPqBDQRlVUt5tay1etgCj5MOg5G_We40nXDy_YrHESvJg2Yo9cYL-YnwiYAZrza4ac-HUEghUFJJ_nVgPY_qkKca42GIykeLMsj-vfeu47Qc6xmEYE3tQnDm97ibvGEGcChv4dnf3pqzVgYKMARy41JKWcff_W-1sP7nzpmAcvBRr9QjILWxGDcxEeQDwzNh5Vi1kXnVjrI0-Zc2utMDo',
    trend: 'down',
    trendValue: '-4%',
  },
];

export function Leaderboard() {
  return (
    <div className="bg-slate-50 min-h-full pb-20">
      {/* Header */}
      <div className="bg-white p-4 border-b border-slate-200 sticky top-0 z-10">
        <h2 className="text-slate-900 text-lg font-bold text-center">Silver League</h2>
      </div>

      {/* League Selector */}
      <nav className="flex border-b border-slate-200 bg-white sticky top-[60px] z-10">
        <a className="flex flex-col items-center justify-center border-b-4 border-transparent text-slate-400 pb-3 pt-4 flex-1 cursor-pointer hover:bg-slate-50">
          <span className="text-sm font-bold uppercase tracking-wider">Bronze</span>
        </a>
        <a className="flex flex-col items-center justify-center border-b-4 border-blue-500 text-blue-500 pb-3 pt-4 flex-1 cursor-pointer bg-blue-50/50">
          <span className="text-sm font-bold uppercase tracking-wider">Silver</span>
        </a>
        <a className="flex flex-col items-center justify-center border-b-4 border-transparent text-slate-400 pb-3 pt-4 flex-1 cursor-pointer hover:bg-slate-50">
          <span className="text-sm font-bold uppercase tracking-wider">Gold</span>
        </a>
      </nav>

      {/* User Status Card */}
      <div className="p-6 text-center border-b border-slate-200 bg-white mb-4">
        <div className="relative mx-auto w-24 h-24 mb-4">
          <div className="absolute inset-0 rounded-full border-4 border-blue-500/30 animate-pulse"></div>
          <img
            src={users.find(u => u.isMe)?.avatar}
            alt="User avatar"
            className="rounded-full w-full h-full border-4 border-blue-500 object-cover"
          />
          <div className="absolute -bottom-2 right-0 bg-blue-500 text-white text-xs font-bold px-2 py-1 rounded-full shadow-lg border-2 border-white">
            12th
          </div>
        </div>
        <h3 className="text-slate-900 text-2xl font-bold">You're in 12th Place</h3>
        <div className="flex items-center justify-center gap-2 mt-1">
          <Zap className="text-blue-500 w-4 h-4 fill-current" />
          <p className="text-slate-500 font-medium">2,450 XP</p>
        </div>
        <p className="text-slate-400 text-xs mt-3 bg-slate-100 inline-block px-3 py-1 rounded-full uppercase font-bold tracking-widest">
          5 days left in League
        </p>
      </div>

      {/* Promotion Zone Label */}
      <div className="px-4 py-3 bg-green-50 flex items-center gap-2 border-y border-green-100">
        <TrendingUp className="text-green-500 w-4 h-4" />
        <p className="text-green-600 text-xs font-bold uppercase tracking-widest">Promotion Zone</p>
      </div>

      {/* Rankings List */}
      <div className="divide-y divide-slate-100 bg-white">
        {users.map((user) => (
          <div
            key={user.rank}
            className={cn(
              "flex items-center gap-4 p-4 transition-colors",
              user.isMe ? "bg-blue-50 border-l-4 border-blue-500" : "hover:bg-slate-50"
            )}
          >
            <span
              className={cn(
                "w-6 text-center font-bold text-lg italic",
                user.rank === 1 ? "text-yellow-500" :
                user.rank === 2 ? "text-slate-400" :
                user.rank === 3 ? "text-orange-400" :
                user.isMe ? "text-blue-500" : "text-slate-400"
              )}
            >
              {user.rank}
            </span>
            <div className="relative h-12 w-12 shrink-0">
              <img
                src={user.avatar}
                alt={user.name}
                className={cn(
                  "rounded-xl w-full h-full object-cover",
                  user.isMe ? "ring-2 ring-blue-500" : ""
                )}
              />
            </div>
            <div className="flex-1 min-w-0">
              <p className={cn("font-bold truncate", user.isMe ? "text-blue-900" : "text-slate-900")}>
                {user.name}
              </p>
              <p className={cn("text-sm", user.isMe ? "text-blue-400" : "text-slate-400")}>
                {user.xp} XP
              </p>
            </div>
            <div
              className={cn(
                "flex items-center gap-1 font-bold text-sm",
                user.trend === 'up' ? "text-green-500" :
                user.trend === 'down' ? "text-red-500" :
                user.trend === 'active' ? "text-blue-500" :
                "text-slate-400"
              )}
            >
              {user.trend === 'up' && <TrendingUp className="w-4 h-4" />}
              {user.trend === 'down' && <TrendingDown className="w-4 h-4" />}
              {user.trend === 'flat' && <Minus className="w-4 h-4" />}
              {user.trend === 'active' && <Zap className="w-4 h-4 fill-current" />}
              {user.trendValue}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
