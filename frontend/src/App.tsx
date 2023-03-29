import { useRoutes } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import router from "./pages/router";
import { ToastContainer } from "react-toastify";

function App() {
  const element = useRoutes(router);

  return (
    <div className="App">
      {element}
      <ToastContainer />
    </div>
  );
}

export default App;
