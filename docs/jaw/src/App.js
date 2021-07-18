import Nav from "./Nav";
import "./App.css"

function App(props) {
    return (
        <>
            <Nav active="main" />
            <div class="jumbotron jumbotron-fluid m-5 fadeIn">
                <div class="container">
                    <div align="center">
                        <div className="titleGroup">
                            <h1 class="display-4">Jaw</h1>
                            <p class="lead">Useful java library</p>
                        </div>
                        <div className="buttonGroup">
                            <button className="btn btn-secondary">Start</button>
                        </div>
                    </div>
                </div>
            </div>
            <div className="container">
                <div className="row">
                    <div className="col-lg-6 col-sm-12">
                        <div className="card">
                            <img src="/book.png" className="card-img-top" alt="Content-Unavailable" />
                            <div className="card-body">
                                <h5 className="card-title">Usage</h5>
                                <p className="card-text">Learn how to use the library</p>
                                <a href="/javadoc" className="btn btn-primary">Let's go</a>
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-6 col-sm-12">
                    <div className="card">
                            <img src="/planet.png" className="card-img-top" alt="Content-Unavailable" />
                            <div className="card-body">
                                <h5 className="card-title">Community</h5>
                                <p className="card-text">Join the community!</p>
                                <a href="/community" className="btn btn-primary">Join</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default App