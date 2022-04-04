import * as React from "react";
import { useEffect, useRef, useState } from "react";

// Require Editor JS files.
import "froala-editor/js/froala_editor.pkgd.min.js";
import "froala-editor/js/plugins.pkgd.min.js";
import "froala-editor/js/third_party/embedly.min.js";
import {addPost} from '../../service/service';

// Require Editor CSS files.
import "froala-editor/css/froala_style.min.css";
import "froala-editor/css/froala_editor.pkgd.min.css";
import "froala-editor/css/third_party/embedly.min.css";

import Froala from "react-froala-wysiwyg";
import FroalaEditorView from "react-froala-wysiwyg/FroalaEditorView";

const FroalaEditor = () => {
  const [model, setModel] = useState("");

  const handleModelChange = (model) => {
    setModel(model);
  };

  const addPostCLick = () => {
    const post = {
      cover:"https://elearningindustry.com/wp-content/uploads/2019/12/the-value-of-employee-training.jpg",
      access: 1,
      priority: 1,
      users:[],
      title:"КТО ТАКАЯ",
      brief:"Личный тренер, специалист по телу Fresh&Yoga Fit. Учусь на кинезиолога. Тренирую с 2016 года. Вела свой проект Interfit, с 2018 года работаю только индивидуально.",
      description: model
    }

    addPost(post);
  }

  return (
    <div className="App">
      <Froala
        model={model}
        onModelChange={handleModelChange}
        tag="textarea"
        config={{
          attribution: false,
          placeholder: "Start typing...",
          toolbarButtons: {
            moreText: {
              buttons: [
                "bold",
                "italic",
                "underline",
                "strikeThrough",
                "subscript",
                "superscript",
                "fontFamily",
                "fontSize",
                "textColor",
                "backgroundColor",
                "inlineClass",
                "inlineStyle",
                "clearFormatting"
              ]
            },
            moreParagraph: {
              buttons: [
                "alignLeft",
                "alignCenter",
                "formatOLSimple",
                "alignRight",
                "alignJustify",
                "formatOL",
                "formatUL",
                "paragraphFormat",
                "paragraphStyle",
                "lineHeight",
                "outdent",
                "indent",
                "quote"
              ]
            },
            moreRich: {
              buttons: [
                "insertLink",
                "insertImage",
                "insertVideo",
                "insertTable",
                "emoticons",
                "fontAwesome",
                "specialCharacters",
                "embedly",
                "insertFile",
                "insertHR"
              ]
            },
            moreMisc: {
              buttons: [
                "undo",
                "redo",
                "fullscreen",
                "print",
                "getPDF",
                "spellChecker",
                "selectAll",
                "html",
                "help"
              ],
              align: "right",
              buttonsVisible: 2
            }
          },
          pluginsEnabled: [
            "table",
            "spell",
            "quote",
            "save",
            "quickInsert",
            "paragraphFormat",
            "paragraphStyle",
            "help",
            "draggable",
            "align",
            "link",
            "lists",
            "file",
            "image",
            "emoticons",
            "url",
            "video",
            "embedly",
            "colors",
            "entities",
            "inlineClass",
            "inlineStyle",
            // 'codeBeautif '
            // 'spellChecker',
            "imageTUI"
          ]
        }}
      /> 
      <button onClick={addPostCLick}>Add</button>
      <br />
      <strong>Read only editor:</strong>
      <FroalaEditorView model={model} />
    </div>
  );
};


export default FroalaEditor;
