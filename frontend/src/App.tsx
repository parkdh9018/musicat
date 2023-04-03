import { useRoutes } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import router from "./pages/router";
import { ToastContainer } from "react-toastify";
import { RadioPlayer } from "./components/broadcast/radioPlayer/RadioPlayer";

function App() {
  const element = useRoutes(router);

  return (
    <div
      className="App"
      style={{ backgroundImage: `url("/img/pagebackground/theme1.png")` }}
    >
      {element}
      <ToastContainer />
      <RadioPlayer />
    </div>
  );
}

export default App;
