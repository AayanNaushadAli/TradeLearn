import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Layout } from '@/components/layout/Layout';
import { Home } from '@/pages/Home';
import { Leaderboard } from '@/pages/Leaderboard';
import { Quests } from '@/pages/Quests';
import { Profile } from '@/pages/Profile';
import { Lesson } from '@/pages/Lesson';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="leaderboard" element={<Leaderboard />} />
          <Route path="quests" element={<Quests />} />
          <Route path="profile" element={<Profile />} />
        </Route>
        <Route path="/lesson/:id" element={<Lesson />} />
      </Routes>
    </BrowserRouter>
  );
}
