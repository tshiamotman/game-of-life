# The Game Of Life

This project was used as an assessment in my final year at WeThinkCode_. I was tasked with creating a backend server that has four endpoints to interact with the Game of Life 2-dimensions world. 

The server has the following enpoints:
1. * Request: POST 
    * url: world/
    * body: 
    ```json
    {
        "command": "DEFINE",
        "data": {
            "name": "string",
            "size": 3,
            "state" : [
                [1, 0, 1],
                [0, 1, 0],
                [0, 0, 0]
            ]
        }
    }
    ```
    * Used to create a new world with an initial state.
2. * Request: GET
    * URL: worlds/
    * Used to get a list of all the worlds saved in the DB.
    
3. * Request: POST
    * URL: /world/{id}/next
    * Used to get the next state for the world with the similar id.

4. * Request: GET
    * URL: /world/{id}/epoch/{epoch}
    * Used to get a specified generation(epoch) state for world with the similar id. 

The project was done in a done in a day and I imported the code from my school's gitlab server. The code for generating the next state I got from Geeks For Geeks.