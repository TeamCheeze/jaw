import Nav from "./Nav";
import firebase from "firebase"
import React from "react";

const firebaseConfig = {
    apiKey: "AIzaSyAzfasSTgCc_-rvsoQMnj028ohBjIa1orI",
    authDomain: "jaw-community.firebaseapp.com",
    projectId: "jaw-community",
    storageBucket: "jaw-community.appspot.com",
    messagingSenderId: "91223723186",
    appId: "1:91223723186:web:c84470018d4f56460b4dab"
};

firebase.initializeApp(firebaseConfig)

const db = firebase.database();

const ref = db.ref("/")

class Community extends React.Component {
    state = {
        a: ""
    }
    componentDidMount() {
        ref.child("/").get().then((snapshot) => {
            if (snapshot.exists()) {
                this.setState({a: JSON.stringify(snapshot.toJSON())})
                console.log(this.state.a)
            }
        })
    }
    getData() {
        if(this.state.a != "") {
            return JSON.parse(this.state.a)["coco"]
        } else {
        }
    }
    render() {
        return(
            <>
            <Nav active="community" />
            {
                this.getData()
            }
        </>
        )
    }
}

export default Community