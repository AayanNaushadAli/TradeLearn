-- 1. Create the Users Profile Table
CREATE TABLE user_profiles (
    id UUID REFERENCES auth.users(id) PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    total_xp INT DEFAULT 0,
    current_streak INT DEFAULT 0,
    hearts INT DEFAULT 5,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 2. Create the Modules Table (e.g., "Crypto Foundations")
CREATE TABLE modules (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT,
    display_order INT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 3. Create the Chapters Table (e.g., "What is Crypto?")
CREATE TABLE chapters (
    id SERIAL PRIMARY KEY,
    module_id INT REFERENCES modules(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    xp_reward INT DEFAULT 50,
    display_order INT NOT NULL
);

-- 4. Create the Lessons Table (Holds your JSON arrays)
CREATE TABLE lessons (
    id SERIAL PRIMARY KEY,
    chapter_id INT REFERENCES chapters(id) ON DELETE CASCADE,
    -- JSONB is perfect here for your polymorphic lesson content
    content_payload JSONB NOT NULL, 
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 5. Enable Row Level Security (RLS)
ALTER TABLE user_profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE modules ENABLE ROW LEVEL SECURITY;
ALTER TABLE chapters ENABLE ROW LEVEL SECURITY;
ALTER TABLE lessons ENABLE ROW LEVEL SECURITY;

-- 6. Create Basic Read Policies (Anyone can read the course content)
CREATE POLICY "Public can read modules" ON modules FOR SELECT USING (true);
CREATE POLICY "Public can read chapters" ON chapters FOR SELECT USING (true);
CREATE POLICY "Public can read lessons" ON lessons FOR SELECT USING (true);