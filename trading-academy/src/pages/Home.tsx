import { Link } from 'react-router-dom';
import { Star, CandlestickChart, Lock, Package2, Brain, TrendingUp } from 'lucide-react';
import { cn } from '@/lib/utils';

export function Home() {
  return (
    <div className="flex-1 overflow-y-auto pb-24 relative no-scrollbar">
      {/* Unit Header */}
      <div className="px-4 pt-6 pb-2 text-center">
        <h2 className="text-slate-500 font-bold text-sm uppercase tracking-widest mb-1">Unit 1</h2>
        <h1 className="text-2xl font-extrabold text-slate-900 mb-2">Market Fundamentals</h1>
        <p className="text-slate-500 text-sm max-w-[260px] mx-auto">
          Master the basics of stocks, charts, and trading psychology.
        </p>
      </div>

      {/* Floating Guidebook Button */}
      <div className="flex justify-between px-6 py-4">
        <button className="flex items-center gap-2 px-4 py-2 rounded-xl bg-slate-800 border-2 border-slate-700 active:translate-y-1 transition-transform">
          <span className="text-slate-400 text-[20px]">📖</span>
          <span className="text-slate-200 font-bold text-xs uppercase tracking-wide">Guidebook</span>
        </button>
      </div>

      {/* The Path */}
      <div className="relative w-full flex flex-col items-center py-4 space-y-4">
        {/* Node 1: Completed (Star) */}
        <div className="relative z-10 group cursor-pointer">
          <div className="absolute -inset-1 bg-yellow-500 rounded-full blur opacity-20 group-hover:opacity-40 transition duration-200"></div>
          <div className="w-20 h-20 rounded-full bg-yellow-400 border-b-4 border-yellow-600 flex items-center justify-center relative active:border-b-0 active:translate-y-1 transition-all">
            <Star className="text-white w-10 h-10 fill-current drop-shadow-md" />
            <div className="absolute -top-3 -right-2 bg-yellow-300 text-yellow-800 text-[10px] font-bold px-2 py-0.5 rounded-full border-2 border-white shadow-sm">
              LVL 5
            </div>
          </div>
        </div>

        {/* Path Connector */}
        <svg className="h-16 w-full text-slate-300" preserveAspectRatio="none" viewBox="0 0 100 60">
          <path
            className="opacity-50"
            d="M50,0 Q50,30 25,60"
            fill="none"
            stroke="currentColor"
            strokeDasharray="12 8"
            strokeLinecap="round"
            strokeWidth="8"
          />
        </svg>

        {/* Node 2: Active (Current Lesson) */}
        <Link to="/lesson/1" className="relative z-10 flex flex-col items-center mr-24 group cursor-pointer">
          {/* Tooltip */}
          <div className="bg-white text-slate-900 px-3 py-2 rounded-xl mb-2 shadow-lg animate-bounce relative border-2 border-slate-100">
            <span className="font-bold text-sm text-blue-500">Start here!</span>
            <div className="absolute bottom-[-6px] left-1/2 -translate-x-1/2 w-3 h-3 bg-white rotate-45 border-b-2 border-r-2 border-slate-100"></div>
          </div>
          
          <div className="w-24 h-24 rounded-full bg-blue-500 border-b-4 border-blue-700 flex items-center justify-center relative active:border-b-0 active:translate-y-1 transition-all shadow-[0_0_15px_rgba(59,130,246,0.5)] group-hover:bg-blue-400">
            {/* Progress Ring SVG */}
            <svg className="absolute inset-0 w-full h-full -rotate-90 pointer-events-none" viewBox="0 0 100 100">
              <circle cx="50" cy="50" fill="none" r="44" stroke="rgba(255,255,255,0.2)" strokeWidth="6" />
              <circle
                cx="50"
                cy="50"
                fill="none"
                r="44"
                stroke="#ffffff"
                strokeDasharray="276"
                strokeDashoffset="69"
                strokeLinecap="round"
                strokeWidth="6"
              />
            </svg>
            <CandlestickChart className="text-white w-10 h-10 fill-current" />
          </div>
          <span className="mt-2 text-slate-400 font-bold text-sm group-hover:text-slate-600 transition-colors">Basics</span>
        </Link>

        {/* Path Connector */}
        <svg className="h-16 w-full text-slate-300" preserveAspectRatio="none" viewBox="0 0 100 60">
          <path
            className="opacity-50"
            d="M25,0 Q25,30 50,60"
            fill="none"
            stroke="currentColor"
            strokeDasharray="12 8"
            strokeLinecap="round"
            strokeWidth="8"
          />
        </svg>

        {/* Node 3: Locked */}
        <div className="relative z-10 ml-24 opacity-60 grayscale hover:grayscale-0 transition-all duration-300 cursor-not-allowed">
          <div className="w-20 h-20 rounded-full bg-slate-200 border-b-4 border-slate-300 flex items-center justify-center relative">
            <div className="absolute inset-0 flex items-center justify-center bg-black/5 rounded-full">
              <Lock className="text-slate-400 w-8 h-8" />
            </div>
          </div>
          <span className="mt-2 text-slate-400 font-bold text-sm text-center block">Forex</span>
        </div>

        {/* Path Connector */}
        <svg className="h-16 w-full text-slate-300" preserveAspectRatio="none" viewBox="0 0 100 60">
          <path
            className="opacity-50"
            d="M50,0 Q75,30 50,60"
            fill="none"
            stroke="currentColor"
            strokeDasharray="12 8"
            strokeLinecap="round"
            strokeWidth="8"
          />
        </svg>

        {/* Node 4: Locked (Chest) */}
        <div className="relative z-10 opacity-70 cursor-not-allowed">
          <div className="w-20 h-20 rounded-full bg-slate-200 border-b-4 border-slate-300 flex items-center justify-center relative">
            <Package2 className="text-yellow-600 w-8 h-8 fill-current" />
          </div>
          <span className="mt-2 text-slate-400 font-bold text-sm text-center block">Bonus</span>
        </div>

        {/* Path Connector */}
        <svg className="h-16 w-full text-slate-300" preserveAspectRatio="none" viewBox="0 0 100 60">
          <path
            className="opacity-50"
            d="M50,0 Q25,30 25,60"
            fill="none"
            stroke="currentColor"
            strokeDasharray="12 8"
            strokeLinecap="round"
            strokeWidth="8"
          />
        </svg>

        {/* Node 5: Locked Big */}
        <div className="relative z-10 mr-24 opacity-50 grayscale cursor-not-allowed">
          <div className="w-24 h-24 rounded-full bg-slate-200 border-b-4 border-slate-300 flex items-center justify-center relative">
            <Brain className="text-purple-400 w-12 h-12" />
            <div className="absolute inset-0 flex items-center justify-center bg-black/5 rounded-full">
              <Lock className="text-slate-400 w-8 h-8" />
            </div>
          </div>
          <span className="mt-2 text-slate-400 font-bold text-sm text-center block">Psychology</span>
        </div>

        {/* Path Divider */}
        <div className="w-full h-px bg-slate-200 my-8 relative">
          <span className="absolute left-1/2 -translate-x-1/2 -top-3 bg-slate-50 px-2 text-slate-400 text-xs font-bold uppercase tracking-wider">
            Unit 2
          </span>
        </div>

        {/* Unit 2 Teaser */}
        <div className="relative z-10 opacity-40 grayscale pb-10">
          <div className="w-24 h-24 rounded-full bg-slate-200 border-b-4 border-slate-300 flex items-center justify-center relative">
            <TrendingUp className="text-blue-400 w-12 h-12" />
          </div>
        </div>
      </div>
    </div>
  );
}
