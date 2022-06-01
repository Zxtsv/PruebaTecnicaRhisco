const title = document.getElementById('staticTitle');
const author = document.getElementById('staticAuthor');
const nameLessor = document.getElementById('staticNameLessor');
const lastName =  document.getElementById('staticLastName');
let dateNow = new Date(2022,10,01);
let dateCreation;

if(dateNow.getUTCMonth()+1<10 && dateNow.getUTCDate()+1<10){
    dateCreation = dateNow.getUTCFullYear() + "-0" + (dateNow.getUTCMonth() + 1) + "-0" + (dateNow.getUTCDate()+1);
}
else if(dateNow.getUTCMonth()+1<10){
    dateCreation = dateNow.getUTCFullYear() + "-0" + (dateNow.getUTCMonth() + 1) + "-" + (dateNow.getUTCDate()+1);
}
else if(dateNow.getUTCDate()+1<10){
    dateCreation = dateNow.getUTCFullYear() + "-" + (dateNow.getUTCMonth() + 1) + "-0" + (dateNow.getUTCDate()+1);
}
else {
    dateCreation = dateNow.getUTCFullYear() + "-" + (dateNow.getUTCMonth() + 1) + "-" + (dateNow.getUTCDate()+1);
}
const dateLoan = document.getElementById('staticDateLoan');

const books = "http://127.0.0.1:8080/books/";
const users = "http://127.0.0.1:8080/users/";

function enviar(){
    fetch(users + "findUser"+"/" + nameLessor.value + "/" + lastName.value)
    .then((res) => res.text())
    .then((data) => {
            let id_lessor;
            let tryNew = false;
            let addUser = false;

            if (data == -1) {
                tryNew = true;
                addUser = true;
            }
            else {
                id_lessor = data;
            }

            if(addUser){
                fetch(users + "addUser", {
                    method: "POST",
                    headers: {
                      "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        name: nameLessor.value,
                        last_name: lastName.value,
                    }),
                })
                    .then((res) => res.json())
                    .then((data) => {})
                    .catch((err) => console.log(err));
            }
            if(tryNew){
                fetch(users + "getLastId")
                                    .then((res) => res.json())
                                    .then((data) => {
                                        id_lessor = data;
                                    })
                                    .catch((err) => console.log(err));
            }

            let book = {
                "id_lessor": id_lessor,
                "title": title.value,
                "date_creation": dateCreation,
                "date_loan": dateLoan.value,
                "author": author.value,
            }
        
            console.log(book);
            fetch(books + "addBook", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(book),
            })
            .then((res) => res.json())
            .then((data) => {})
            .catch((err) => console.log(err));
        })
        .catch((err) => console.log(err));
}