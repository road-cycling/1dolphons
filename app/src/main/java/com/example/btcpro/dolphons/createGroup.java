package com.example.btcpro.dolphons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;

public class createGroup extends AppCompatActivity {

    private Button createGroup;
    private CheckBox privateGroup;
    private ImageButton groupPicture;
    private TextView groupName;
    private TextView groupDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        /*
        nameText = (TextView)findViewById(R.id.name_text);
        nameButton = (Button)findViewById(R.id.name_button);

        nameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainActivity.this.nameText.setText(R.string.name_text);
            }
        });
        */
    }
}


/*
  const { currentUser } = firebase.auth();
  const { uid } = currentUser;
  const lower_name = name.toLowerCase();
  ///users/${currentUser.uid}/groups
  return async dispatch => {
    try {
      //how data is structured
      dispatch({ type: GROUP_CREATE_PRESS })
      //const response = await firebase.database().ref(`/groups/${name}${uid}`)
      const response = await firebase.database().ref(`/groups/`)
        .push({
           name,
          organizer,
          summary,
          isPublic,
          lower_name,
          owner: uid,
        })
      const { key } = response
      //console.log(key);
      await firebase.database().ref(`/users/${uid}`).push({ key })
      await firebase.database().ref(`/events/${key}`).push({
        location: 'Group Created',
        summary: 'Group Created',
        chosenDate: new Date().toString()
      })
      dispatch({ type: GROUP_CREATE_SUCCESS })
      return;
    } catch (e) { console.log(e) }
  }

  //sign in
    const user = await firebase.auth().signInWithEmailAndPassword(email, password);


//create user
const user = await firebase.auth().createUserWithEmailAndPassword(email, password);
//add name to user profile so we can say "hello x"
        await user.updateProfile({ displayName: name })





export const newImage = (uri, mime = 'image/jpg') => {
        //dispatch({  })
        return dispatch => {
        return new Promise((resolve, reject) => {
        const uploadUri = Platform.OS === 'ios' ? uri.replace('file://', '') : uri
        const Blob = RNFetchBlob.polyfill.Blob
        const fs = RNFetchBlob.fs
        window.XMLHttpRequest = RNFetchBlob.polyfill.XMLHttpRequest
        window.Blob = Blob

        let uploadBlob = null
        const { currentUser } = firebase.auth();
        const { uid } = currentUser;
        const imageRef = firebase.storage().ref(`/posts/${uid}`)
        fs.readFile(uploadUri, 'base64')
        .then((data) => {
        return Blob.build(data, { type: `${mime};BASE64` })
        })
        .then((blob) => {
        uploadBlob = blob
        return imageRef.put(blob, { contentType: mime })
        })
        .then(() => {
        uploadBlob.close()
        return imageRef.getDownloadURL()
        })
        .then((url) => {
        currentUser.updateProfile({ photoURL : url })
        console.log('done')
        dispatch({
        type: IMAGE_UPLOAD_WELCOME,
        payload: url
        })
        })
        .catch((error) => {
        console.log(error)
        console.log('thiscodesucks')
        })
        })
        }
        }




        var { displayName, photoURL } =  firebase.auth().currentUser;
        dispatch({
        type: NAME_CHANGE_WELCOME,
        payload: displayName
        })
        dispatch({
        type: IMAGE_UPLOAD_WELCOME,
        payload: photoURL
        })



        const response = await firebase.database().ref(`/events/${this.state.data}`)
        .push({
        location,
        summary,
        chosenDate: chosenDate.toString()
        })
        this.props.history.push(`/getGroup/${this.state.data}`)

        group id//
 */