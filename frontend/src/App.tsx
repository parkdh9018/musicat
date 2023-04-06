import { useRoutes } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import router from "./pages/router";
import { ToastContainer } from "react-toastify";
import lightWood from "@/asset/img/lightWood.png";

function App() {
  const element = useRoutes(router);

  return (
    <div
      className="App"
      style={{
        backgroundImage: `url(${lightWood})`,
      }}
    >
      {element}
      <ToastContainer limit={1}/>
    </div>
  );
}

export default App;
