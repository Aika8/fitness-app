import React from "react";
import Navbar from "../navbar";
import Posts from './posts';
import "./home.css";
const Home = (props) => {
    return (
        <div>
            <Navbar authenticated={props.authenticated} onLogout={props.onLogout}/>
            <section className="motivation">
                <div>
                    <h2>Тренировки не самоцель.</h2>
                    <p>
Это средство, чтобы каждый день были силы пробовать новое, заниматься любимыми хобби, <br/>путешествовать и кайфовать от жизни.
                    </p>
                </div>
            </section>
            <Posts/>
        </div>
    )
}

export default Home;