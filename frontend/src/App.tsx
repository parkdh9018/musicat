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
      style={{
        backgroundImage: `url("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcR4G11_HHGk8F9G7amiuGckeFxC1iJRAS_LL9LvQa06afN6mk9P")`,
      }}
    >
      {element}
      <ToastContainer />
      <RadioPlayer />
    </div>
  );
}

export default App;
