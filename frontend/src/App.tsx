import "./App.css";
import router from "@/pages/router";
import { useRoutes } from "react-router-dom";
import { Header } from "./components/header/Header";
import { Broadcast } from "./components/broadcast/Broadcast";
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
function App() {
  const element = useRoutes(router);
  return (
    <div className="App">
      <Header />
      <div
        style={{
          backgroundImage: "url(/img/pagebackground/theme1.png)",
          width: "100%",
        }}
      >
        <Broadcast />
      </div>
      {element}
      <ToastContainer/>
    </div>
  );
}

export default App;
