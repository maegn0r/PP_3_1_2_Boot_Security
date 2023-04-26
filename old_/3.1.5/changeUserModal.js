function drawChangeModal(id) {

    console.log('Tub edit user №' + id + ' push!');
    let url = 'http://localhost:8080/api/v1/admin/users/' + id;
    let body = ''
    fetch(url)
        .then(response => response.json())
        .then(data => changingData(data))
        .catch(error => console.log(error))
    const changingData = (data) => {
        console.log(data)

        let roles = []
        data.listOfStringNameOfRolesWithoutRole.forEach((val) => {
            roles.push(val);
        })

        const select = document.querySelector('#edit_role').getElementsByTagName('option');

        for (let i = 0; i < select.length; i++) {
            if (select[i].value === userEdit.roles[i].role) {
                select[i].selected = true;
                if (i === select.length - 1) {
                    break;
                }
            } else if (select[i + 1].value === userEdit.roles[i].role) {
                select[i + 1].selected = true;
            }
        }
        // function getSelectedOptions(oList)
        // {
        //     var sdValues = [];
        //     for(var i = 1; i < oList.options.length; i++)
        //     {
        //         if(oList.options[i].selected == true)
        //         {
        //             sdValues.push(oList.options[i].value);
        //         }
        //     }
        //     return sdValues;
        // }

        body += `
        <form id="addUserForm" class="needs-validation">
             <input id='id' type="hidden" value="${data.id}">
           <div class="form-group">
             <p class="text-center my-0"><strong> First name </strong></p>
             <input class="form-control mt-0" type="text" id="name" value="${data.name}"/>
           </div>
           <div class="form-group align-items-center justify-content-center">
             <p class="text-center my-0"><strong> Last name </strong></p>
             <input class="form-control mt-0" type="text" id="surname" value="${data.surname}"/>
           </div>
           <div class="form-group">
             <p class="text-center my-0"><strong> Age </strong></p>
             <input class="form-control mt-0" type="number" id="age" value="${data.age}"/>
           </div>
           <div class="form-group">
             <p class="text-center my-0"><strong> Email </strong></p>
             <input class="form-control mt-0" type="email" id="email" value="${data.username}"/>
           </div>
           <div class="form-group">
             <p class="text-center my-0"><strong> Password </strong></p>
             <div>
               <input class="form-control w-100 mt-0" type="password" id="pass" value="${data.password}" name="pass"/>
             </div>
           </div>
           <div class="form-group">
             <p class="text-center my-0"><strong> Role </strong></p>
             <select multiple class="form-control p-0 m-0" id="roles" name="roles4change">
               <option value="1" ${roles.includes('USER') ? 'selected' : ''}>USER</option>
               <option value="2" ${roles.includes('ADMIN') ? 'selected' : ''}>ADMIN</option>
             </select>
           </div>
        </form>`
        document.getElementById('changing').innerHTML = body;
    }
}
