$(() => $.get('https://v2.jokeapi.dev/joke/Any?lang=fr', (response) => $('#joke').html(`
    <p>${response.setup}<p/>
    <span>${response.delivery}</span>
`)));