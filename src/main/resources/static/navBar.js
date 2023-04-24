fetch('http://localhost:8080/api/v1/user/')
    .then(response => response.json())
    .then(data => {
        bodyRoles = []
        data.roles.forEach(role => {
           bodyRoles.push(role.role)
        });
        document.getElementById("navbar").innerHTML =
           `<span style="font-weight: bolder">${data.username}</span>
            <span> with roles: </span>
            <span>${data.rolesAsString}</span>`;
    })
    .catch(error => console.log(error));
