import React, { useState, useRef } from 'react'
import ReactDOM from 'react-dom'
import axios from 'axios'
import { HiOutlineFolderDownload } from 'react-icons/hi'

const App = () => {

    const [selectedFiles, setSelectedFiles] = useState(undefined);
    const [currentFile, setCurrentFile] = useState(undefined);
    const [message, setMessage] = useState("");
    const input = useRef(null)

    // text on button is either filename or "select xml-file"
    const filename = input.current?.value ? input.current.value.split('\\').pop() : "Select XML-file"

    // download is disabled if file is not selected
    const isDisabled = input.current?.value ? false : true

    const selectFile = (event) => {
        setSelectedFiles(event.target.files);
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        let currentFile = selectedFiles[0];
        setCurrentFile(currentFile);
        const formData = new FormData();
        formData.append("file", currentFile);

        // posting xml.file to the server
        axios.post('/xmltocsv', formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            }

            // receiving csv in responce and downloading it
        }).then(({ data }) => {
            const downloadUrl = window.URL.createObjectURL(new Blob([data]));
            const link = document.createElement('a');
            link.href = downloadUrl;
            link.setAttribute('download', 'invoice.csv');
            document.body.appendChild(link);
            link.click();
            link.remove();
        })
            .catch(error => {
                console.error('There was an error!', error);
                setMessage("Sorry! There was an unexpected error processing request");
                setCurrentFile(undefined);
            })
        setSelectedFiles(undefined);
    };

    return (
        <div className='wrapper' >
            <section className="card" >
                <div className="card-content" >
                    <div className="alert alert-light" role="alert" > {message} </div>
                    <h1 > Convert XML invoice file into CSV format </h1>
                    <form onSubmit={handleSubmit} >
                        <label className='btn selectFile' >
                            <input ref={input} className="fileInput" type="file" accept=".xml" onChange={selectFile} aria-label="select xml-file" />
                            <span > {filename} </span>
                        </label>
                        <button className='btn download' disabled={isDisabled} type="submit" >
                            <span className="icon" > < HiOutlineFolderDownload /> </span>Download CSV
                        </button>
                    </form>
                </div>
            </section>
        </div>)
}
export default App

ReactDOM.render(<App />,
    document.getElementById('react')
)