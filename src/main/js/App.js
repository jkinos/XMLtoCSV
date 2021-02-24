import React from 'react'
import ReactDOM from 'react-dom'
import Axios from 'axios'

const App = () => {

    return (
    <div>
        <input type="file" accept=".xml"></input>
    </div>
    )
}
export default App

ReactDOM.render(
	<App />,
	document.getElementById('react')
)