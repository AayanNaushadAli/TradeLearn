import { create } from 'zustand';

interface UserState {
  xp: number;
  gems: number;
  hearts: number;
  streak: number;
  league: string;
  completedLessons: number;
  addXp: (amount: number) => void;
  removeHeart: () => void;
  completeLesson: () => void;
}

export const useStore = create<UserState>((set) => ({
  xp: 12450,
  gems: 450,
  hearts: 5,
  streak: 12,
  league: 'Silver',
  completedLessons: 156,
  addXp: (amount) => set((state) => ({ xp: state.xp + amount })),
  removeHeart: () => set((state) => ({ hearts: Math.max(0, state.hearts - 1) })),
  completeLesson: () => set((state) => ({ completedLessons: state.completedLessons + 1, xp: state.xp + 20 })),
}));
