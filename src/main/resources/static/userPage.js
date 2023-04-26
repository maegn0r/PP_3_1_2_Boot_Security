const data = document.getElementById("tableUserBody");
const url = 'http://localhost:8080/api/v1/user';
const panel = document.getElementById("user-header");

function userAuthInfo() {
    fetch(url)
        .then((res) => res.json())
        .then((user) => {

            let temp = '';

            temp += `<tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.age}</td>
            <td>${user.username}</td>
            <td>${user.rolesAsString}</td>
            </tr>`;
            data.innerHTML = temp;
            panel.innerHTML = `<h5>${user.username} with roles: ${user.rolesAsString}</h5>`
        });
}

userAuthInfo()

