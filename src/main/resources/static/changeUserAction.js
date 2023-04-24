function changeUserAction() {
    let id = document.getElementById('id').value;
    let tr = document.getElementById('tr№'+id);
    // получаем массив int из формы select по id="roles" и name="roles4change"
    let roles = $('#roles').val();
    class Role {
        constructor(id, role) {
            this.id = id;
            this.role = role;
        }
    }
    let currentRoles = [];
    for(let i = 0; i<roles.length; i++) {
        const id = roles[i];
        const role = id == 1 ? `ROLE_USER` : `ROLE_ADMIN`
        currentRoles.push(new Role(id, role))
    }
    const user = {
        id: document.getElementById('id').value,
        firstname: document.getElementById('firstname').value,
        lastname: document.getElementById('lastname').value,
        age: document.getElementById('age').value,
        email: document.getElementById('email').value,
        password: document.getElementById('pass').value,
        roles: currentRoles
    };
    fetch('http://localhost:8080/admin/users/',{
        method: 'PUT',
        headers: {
            'Content-Type' : 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    })
        .then(response => response.json())
        .then(data => {
            console.log('User №' + id + 'edited')
            let rolesAsString = '';
            for (let a = 0; a < data.roles.length; a++) {
                rolesAsString += data.roles[a].role.substring(0, 5);
                rolesAsString += a<(data.roles.length-1) ? ", " : "";
            }
            tr.innerHTML = `
                <td id="id:${data.id}">${data.id}</td>
                <td id="firstname:${data.id}">${data.firstname}</td>
                <td id="lastname:${data.id}">${data.lastname}</td>
                <td id="age:${data.id}">${data.age}</td>
                <td id="email:${data.id}">${data.email}</td>
                <td id="roles:${data.id}">${rolesAsString}</td>
                <td>
                    <div class="all-classes-container">
                    <button id="changeButton" type="button" class="btn btn-primary btn-sm" data-toggle="modal"
                            data-target="#changeModal" data-userID="${data.id}">
                        Edit
                    </button>
                </div>
                </td>
                <td>
                    <button id="deleteButton" type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                            data-target="#deleteModal" data-userID="${data.id}">
                        Delete
                    </button>
                </td>`
        })
        .catch(error => {
            console.log("Error edited data user №" + id + " " + error.message);
        }).finally( ()=>{redirectingToStart();});
}