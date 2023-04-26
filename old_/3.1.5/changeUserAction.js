function changeUserAction() {
    let id = document.getElementById('id').value;
    let tr = document.getElementById('tr№'+id);
    // получаем массив int из формы select по id="roles" и name="roles4change"
    // let roles = $('#roles').val();
    // class Role {
    //     constructor(id, role) {
    //         this.id = id;
    //         this.role = role;
    //     }
    // }
    // let currentRoles = [];
    // for(let i = 0; i<roles.length; i++) {
    //     const id = roles[i];
    //     const role = id == 1 ? `ROLE_USER` : `ROLE_ADMIN`
    //     currentRoles.push(new Role(id, role))
    // }
    const user = {
        id: document.getElementById('id').value,
        firstname: document.getElementById('name').value,
        lastname: document.getElementById('surname').value,
        age: document.getElementById('age').value,
        email: document.getElementById('email').value,
        password: document.getElementById('pass').value,
        roles: document.getElementById('roles').value
    };
    fetch('http://localhost:8080/api/v1/admin/users',{
        method: 'PUT',
        headers: {
            'Content-Type' : 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    })
        .then(response => response.json())
        .then(data => {
            console.log('User №' + id + 'edited')
            tr.innerHTML = `
                <td id="id:${data.id}">${data.id}</td>
                <td id="firstname:${data.id}">${data.name}</td>
                <td id="lastname:${data.id}">${data.surname}</td>
                <td id="age:${data.id}">${data.age}</td>
                <td id="email:${data.id}">${data.email}</td>
                <td id="roles:${data.id}">${data.listOfStringNameOfRolesWithoutRole}</td>
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