import "./App.css";
import router from "@/pages/router";
import { useRoutes } from "react-router-dom";
import { Header } from "./components/header/Header";
import { Broadcast } from "./components/broadcast/Broadcast";

function App() {
  const element = useRoutes(router);
  return (
    <div className="App">
      <Header />
      <div
        style={{
          backgroundImage: "url(../../../public/img/pagebackground/theme1.png)",
          width: "100vw",
        }}
      >
        <Broadcast />
      </div>
      {element}
    </div>
  );
}

export default App;
