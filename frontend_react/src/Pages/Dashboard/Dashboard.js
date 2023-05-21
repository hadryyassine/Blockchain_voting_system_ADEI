import Sidebar from "../../components/Sidebar/Sidebar";
import CardList from "../../components/CardList/CardList";
import "./Dashboard.css";
import Adei from "../../images/adei.png";

export default function DashboardPage() {
  return (
    <>
      <div className="container" style={{ backgroundImage: `url(${Adei})`, backgroundRepeat: "repeat", backgroundSize: "20%", }}>
        <div className="row">
          <Sidebar />
          <CardList />
        </div>
      </div>
    </>
  );
}
