# 1dolphons

William: Log in / sign up, View Group, Create Event
Sergio: createGroup, Messaging
Tyler: createGroup, Messaging
Trenton: editUserProfile, changeUserPassword
Nathon:

Finished:
Will:
Serg:
ricet:
Trenton: welcome, editUserProfile, editUserName, changeUserPassword
Nate:

To-Do: 
-*Connect all screens when buttons are pressed (Welcome Page should do this- make sure all other pages that need it have it)


Because we used noSQL some things are harder to do. The point of noSQL is to
allow for much faster reads, we are able to achieve this by replicating data.
users - userID - groupsApartOf - { groupName, groupID } (we need the id incase they click for the following query)
groups - groupID - (events | users) - (users: { userName, userID }) : (events: { eventinfo })(we dont want  to make a query for each user in the group so we store the name separately)
This makes deleting and editing info somewhat of a challenge
events are fine though as its only stored in one place.

So if we want to delete a user, we need to get the ID the of the groups the
user was a part of, delete all those references to him in that group and then finally delete /user/userid/*

Same with if a user changes his name, we will have to update it in the user section
but also update it in every group hes apart of, we can have a separate(?) class that
does this or we could use cloud functions. (this is why i think we should leave it till later).

Product Backlog:
  - Add faint loading emoticon on load
  - get rid of sign out(or keep it and wire it up correctly) and make a chat (where you can add other user id(how would we do this)? not sure need to agree on this... would be fun)
  - make a page to view a group (see old design, this will show name/ organizer/ summary / past events and upcoming events  )
    (when above is done we can add 1. admin tools, 2. ability for admin to delete users. 3. ability to join / leave group etc so this is important(will open doors for alot of new items to do))
  - join group page, where you are able to search for grousp, this would make a firebase query, and display the results on a (recycler view~~ or array adapter like prev)
  
  video for chat msg
  https://www.youtube.com/watch?v=Xn0tQHpMDnM
