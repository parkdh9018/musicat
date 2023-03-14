import { useRoutes } from "react-router-dom";
import "./App.css";
import router from "./pages/router";

function App() {
  const element = useRoutes(router);
  return (
    <div className="App">
      {/* <Routes>
        <Route path="/" element={<Broadcast />} />
      </Routes> */}
      {element}
    </div>
  );
}

export default App;
