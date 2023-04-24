function deleteUserAction() {
    let id = document.getElementById('id').value;
    let tr = document.getElementById('tr№'+id);
    fetch(('http://localhost:8080/api/v1/admin/users/' + id),{
        method: 'DELETE',
    })
        .then(response => response.json())
        .then(() => {
            console.log('User № ' + id + 'deleted')
            tr.remove();
        })
        .catch(error => {
            console.log("Error, user №" + id + "not deleted " + error.message);
        });
}