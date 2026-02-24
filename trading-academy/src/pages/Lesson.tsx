import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { X, Heart, CheckCircle } from 'lucide-react';
import { cn } from '@/lib/utils';
import { useStore } from '@/store/useStore';

export function Lesson() {
  const navigate = useNavigate();
  const { hearts, completeLesson } = useStore();
  const [selectedOption, setSelectedOption] = useState<number | null>(null);
  const [isChecked, setIsChecked] = useState(false);
  const [isCorrect, setIsCorrect] = useState(false);

  const handleCheck = () => {
    if (selectedOption === null) return;
    
    // Correct answer is 1 (A)
    const correct = selectedOption === 1;
    setIsCorrect(correct);
    setIsChecked(true);

    if (correct) {
      // Play success sound (optional)
    } else {
      // Play error sound (optional)
      // removeHeart(); // In a real app
    }
  };

  const handleContinue = () => {
    if (isCorrect) {
      completeLesson();
      navigate('/');
    } else {
      // Reset or retry logic
      setIsChecked(false);
      setSelectedOption(null);
    }
  };

  return (
    <div className="bg-white min-h-screen flex flex-col relative overflow-hidden">
      {/* Header */}
      <header className="flex items-center gap-4 p-4 pt-6 bg-white shrink-0 z-10">
        <button 
          onClick={() => navigate('/')}
          className="text-slate-400 hover:bg-slate-100 rounded-full p-2 transition-colors"
        >
          <X className="w-6 h-6" />
        </button>
        <div className="flex-1 h-4 bg-slate-100 rounded-full overflow-hidden relative">
          <div 
            className="absolute left-0 top-0 h-full bg-green-500 rounded-full transition-all duration-500 ease-out" 
            style={{ width: '40%' }}
          >
            <div className="absolute top-1 right-2 w-4 h-1.5 bg-white/30 rounded-full"></div>
          </div>
        </div>
        <div className="flex items-center gap-1 text-red-500 font-bold text-lg">
          <Heart className="w-6 h-6 fill-current" />
          <span>{hearts}</span>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 flex flex-col overflow-y-auto pb-32 px-4 w-full max-w-lg mx-auto">
        <h1 className="text-2xl font-bold text-center mt-2 mb-6 text-slate-900 leading-tight">
          What does this green candle represent?
        </h1>

        {/* Chart Graphic */}
        <div className="w-full flex justify-center mb-8 relative">
          <div className="bg-white border-2 border-slate-100 rounded-2xl p-6 w-full shadow-sm flex items-center justify-center aspect-[4/3] max-h-64 relative overflow-hidden">
            {/* Grid Background */}
            <div 
              className="absolute inset-0 opacity-5 pointer-events-none" 
              style={{ backgroundImage: 'radial-gradient(circle, currentColor 1px, transparent 1px)', backgroundSize: '20px 20px' }}
            ></div>
            
            {/* Candlestick Graphic */}
            <div className="relative flex items-center justify-center gap-8 h-40">
              {/* Previous Candle (Bearish/Red) */}
              <div className="flex flex-col items-center opacity-40">
                <div className="w-0.5 h-6 bg-red-500/50"></div>
                <div className="w-4 h-12 bg-red-500/50 rounded-sm"></div>
                <div className="w-0.5 h-6 bg-red-500/50"></div>
              </div>
              
              {/* Target Candle (Bullish/Green) */}
              <div className="flex flex-col items-center relative group">
                {/* Label line */}
                <div className="absolute -right-16 top-1/2 -translate-y-1/2 flex items-center gap-2">
                  <div className="w-8 h-[2px] bg-blue-500"></div>
                  <span className="text-xs font-bold text-blue-500 bg-blue-50 px-2 py-1 rounded">Target</span>
                </div>
                <div className="w-1 h-8 bg-green-500"></div>
                <div className="w-8 h-20 bg-green-500 rounded-[2px] shadow-[0_0_15px_rgba(34,197,94,0.4)]"></div>
                <div className="w-1 h-8 bg-green-500"></div>
              </div>
              
              {/* Next Candle placeholder */}
              <div className="flex flex-col items-center opacity-20">
                <div className="w-0.5 h-4 bg-gray-500"></div>
                <div className="w-4 h-8 bg-gray-500 border border-gray-400 rounded-sm"></div>
                <div className="w-0.5 h-4 bg-gray-500"></div>
              </div>
            </div>
          </div>
        </div>

        {/* Options */}
        <div className="flex flex-col gap-3 w-full">
          {[
            { id: 1, label: 'A', text: 'Price opened lower and closed higher' },
            { id: 2, label: 'B', text: 'Price opened higher and closed lower' },
            { id: 3, label: 'C', text: 'Price stayed exactly the same' },
            { id: 4, label: 'D', text: 'Market is closed for the day' },
          ].map((option) => (
            <label
              key={option.id}
              className={cn(
                "group relative flex items-center p-4 rounded-xl border-2 border-slate-200 cursor-pointer transition-all active:scale-[0.99] hover:bg-slate-50",
                selectedOption === option.id && "border-blue-400 bg-blue-50",
                isChecked && isCorrect && selectedOption === option.id && "border-green-500 bg-green-100",
                isChecked && !isCorrect && selectedOption === option.id && "border-red-500 bg-red-100",
                isChecked && !isCorrect && option.id === 1 && "border-green-500 bg-green-100" // Show correct answer if wrong
              )}
              onClick={() => !isChecked && setSelectedOption(option.id)}
            >
              <div className={cn(
                "flex items-center justify-center size-8 rounded-lg border-2 border-slate-200 mr-4 text-slate-400 font-bold transition-colors",
                selectedOption === option.id && "border-blue-400 text-blue-500",
                isChecked && isCorrect && selectedOption === option.id && "border-green-500 bg-green-500 text-white",
                isChecked && !isCorrect && selectedOption === option.id && "border-red-500 bg-red-500 text-white",
                isChecked && !isCorrect && option.id === 1 && "border-green-500 bg-green-500 text-white"
              )}>
                {option.label}
              </div>
              <span className={cn(
                "text-base font-medium text-slate-700",
                isChecked && isCorrect && selectedOption === option.id && "text-green-700",
                isChecked && !isCorrect && selectedOption === option.id && "text-red-700"
              )}>
                {option.text}
              </span>
            </label>
          ))}
        </div>
      </main>

      {/* Footer */}
      <footer className={cn(
        "fixed bottom-0 w-full p-4 border-t z-20 transition-colors",
        isChecked ? (isCorrect ? "bg-green-100 border-green-200" : "bg-red-100 border-red-200") : "bg-white border-slate-200"
      )}>
        <div className="max-w-lg mx-auto w-full flex justify-between items-center">
          {isChecked && (
            <div className="flex items-center gap-3">
              <div className={cn(
                "w-10 h-10 rounded-full flex items-center justify-center",
                isCorrect ? "bg-green-500" : "bg-red-500"
              )}>
                {isCorrect ? <CheckCircle className="text-white w-6 h-6" /> : <X className="text-white w-6 h-6" />}
              </div>
              <div>
                <h3 className={cn("font-bold text-lg", isCorrect ? "text-green-700" : "text-red-700")}>
                  {isCorrect ? "Nicely done!" : "Incorrect"}
                </h3>
                {!isCorrect && <p className="text-red-600 text-sm">Correct answer: A</p>}
              </div>
            </div>
          )}
          
          <button
            onClick={isChecked ? handleContinue : handleCheck}
            disabled={!selectedOption}
            className={cn(
              "ml-auto px-8 py-3 rounded-xl font-bold text-sm uppercase tracking-wide shadow-[0_4px_0_0_rgba(0,0,0,0.2)] active:translate-y-[4px] active:shadow-none transition-all",
              isChecked 
                ? (isCorrect ? "bg-green-500 text-white shadow-green-700" : "bg-red-500 text-white shadow-red-700")
                : (selectedOption ? "bg-green-500 text-white shadow-green-700 hover:bg-green-400" : "bg-slate-200 text-slate-400 shadow-slate-300 cursor-not-allowed")
            )}
          >
            {isChecked ? "Continue" : "Check"}
          </button>
        </div>
      </footer>
    </div>
  );
}
