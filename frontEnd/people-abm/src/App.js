import { Routes, Route } from "react-router-dom";
import People from "./pages/People";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<People />} />
      </Routes>
    </div>
  );
}

export default App;
