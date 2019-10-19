# Infinite scroll sample

This project demonstrates how to create an infinite scroll of fetched items.
This project will be using [IMDB public API](https://developers.themoviedb.org/3/getting-started/introduction) in order to obtain a list of popular TV shows, 
then it will show them in a scrollable list.
The load will be page-based, an initial set of items will be retrieved and when the user
scrolls down then another page of results will be dynamically loaded and added to the scroll.

## How to run this sample

You must have a valid API key of the [IMDB public API](https://developers.themoviedb.org/3/getting-started/introduction) in order to run this sample app. Once you 
have the key, please follow this steps to run this project in your device or emulator:

- Download the source code
- Create a file in the root directory called apikey.properties
- Create a new line with the following content, substituting <your_key> for you valid API key: `IMDB_API_KEY="<your_key>"`
- Now you are ready to build and execute the app.

## Image caching

We are going to load a small thumbnail of each element. Two main libraries were analyzed: [Picasso](https://square.github.io/picasso/) and [Glide](https://github.com/bumptech/glide). We are going to use Glide because it stores the preview pictures with the final image size, hence the loading from cache should be faster.