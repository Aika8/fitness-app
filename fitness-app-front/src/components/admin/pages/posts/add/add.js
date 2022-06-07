import React,{useState} from "react";
import { Editor } from '@tinymce/tinymce-react';
import { addPost } from "../../../../../service/service";
import Navbar from "../../../navbar";
import Nav from '../../../nav';
import { useNavigate, useParams } from 'react-router-dom'

const Add = () => {
    const navigate = useNavigate();
    const [cover, setCover] = useState("");
    const [title, setTitle] = useState("");
    const [brief, setBrief] = useState("");
    const [text, setText] = useState("");

    const addPostCLick = () => {
        const post = {
          cover: cover,
          access: 1,
          priority: 1,
          users:[],
          title:title, 
          brief:brief,
          description: text
        }
                
        addPost(post, localStorage.getItem("accessToken"))
            .then(res=> {
                if(res.status === 200) {
                    navigate("/admin/posts");
                } else { 
                    alert("Error");
                }
            });   
      }

      return (

    <div id="wrapper">

    <Navbar />
    <div id="content-wrapper" class="d-flex flex-column">

        <div id="content">
            <Nav />
            <div className="container">
            <div className="row">
            <div className="col-8">
                <form>
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input onChange={(e) => {
                            setTitle(e.target.value)
                        }} type="text" class="form-control" id="title" aria-describedby="emailHelp" placeholder="Title" />
                    </div>
                    <div class="form-group">
                        <label for="cover">Cover</label>
                        <input onChange={(e) => {
                            setCover(e.target.value)
                        }}
                            ype="text" class="form-control" id="cover" placeholder="Cover" />
                    </div>
                    <div class="form-group">
                        <label for="brief">Brief</label>
                        <input
                            onChange={(e) => {
                                setBrief(e.target.value)
                            }}
                            type="text" class="form-control" id="brief" placeholder="Brief" />
                    </div>
                </form>
            </div>
            <div className="col-4">
                <div className="card">
                    <img className="card-img-top" src={cover} alt="Card image cap" />
                    <div className="card-body">
                        <h5 className="card-title">{title}</h5>
                        <p className="card-text">{brief}</p>
                    </div>
                </div>
            </div>
        </div>
        <Editor apiKey="f7w7f8uiq7dm2l5bo7bow5nnspwloapwjf0vyx7ph2jqign9"
            outputFormat='text'
            onEditorChange={(newText) => setText(newText)}
            init={{
                selector: 'textarea#open-source-plugins',
                plugins: 'print preview paste importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists wordcount imagetools textpattern noneditable help charmap quickbars emoticons',
                imagetools_cors_hosts: ['picsum.photos'],
                menubar: 'file edit view insert format tools table help',
                toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media template link anchor codesample | ltr rtl',
                toolbar_sticky: true,
                autosave_ask_before_unload: true,
                autosave_interval: '30s',
                autosave_prefix: '{path}{query}-{id}-',
                autosave_restore_when_empty: false,
                autosave_retention: '2m',
                image_advtab: true,
              
                importcss_append: true,
                file_picker_callback: function (callback, value, meta) {
                  /* Provide file and text for the link dialog */
                  if (meta.filetype === 'file') {
                    callback('https://www.google.com/logos/google.jpg', { text: 'My text' });
                  } 
              
                  /* Provide image and alt text for the image dialog */
                  if (meta.filetype === 'image') {
                    callback('https://www.google.com/logos/google.jpg', { alt: 'My alt text' });
                  }
              
                  /* Provide alternative source and posted for the media dialog */
                  if (meta.filetype === 'media') {
                    callback('movie.mp4', { source2: 'alt.ogg', poster: 'https://www.google.com/logos/google.jpg' });
                  }
                },
                template_cdate_format: '[Date Created (CDATE): %m/%d/%Y : %H:%M:%S]',
                template_mdate_format: '[Date Modified (MDATE): %m/%d/%Y : %H:%M:%S]',
                height: 600,
                image_caption: true,
                quickbars_selection_toolbar: 'bold italic | quicklink h2 h3 blockquote quickimage quicktable',
                noneditable_noneditable_class: 'mceNonEditable',
                toolbar_mode: 'sliding',
                contextmenu: 'link image imagetools table',
                skin: false ? 'oxide-dark' : 'oxide',
                content_css: false ? 'dark' : 'default',
                content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
            }}
        />
        <button onClick={addPostCLick} className="btn btn-secondary">Add</button>            
            </div>

        </div>
    </div>

    </div>

        
      )

}

export default Add;