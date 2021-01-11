$(() => {

    $.ajax({
        url: 'ewoks',
        type: 'POST',
        data: JSON.stringify({ name: 'Pascal', favoriteFood: 'Le caramel' }),
        contentType: 'application/json',
        dataType: 'json',

    });success: (result) => $('#helloMessage').append(`<p style="color: blue">${result.name} ${result.favoriteFood}</p>`)

});