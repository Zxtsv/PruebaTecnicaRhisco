const app = document.querySelector("#app");

const url = "http://127.0.0.1:8080/books/";
const users = "http://127.0.0.1:8080/users/";

fetch(url)
  .then((res) => res.json())
  .then((data) => {
    data.forEach((book) => {
      const tr = document.createElement("tr");

      let id = document.createElement("th");
      id.setAttribute("scope", "row");
      id.innerHTML = book.id;
      tr.appendChild(id);

      let title = document.createElement("td");
      title.innerHTML = book.title;
      tr.appendChild(title);

      let author = document.createElement("td");
      author.innerHTML = book.author;
      tr.appendChild(author);

      let nameLessor = document.createElement("td");
      fetch(users + book.id_lessor)
        .then((res) => res.json())
        .then((data) => {
          nameLessor.innerHTML = data.name + " " + data.last_name;
          tr.appendChild(nameLessor);
          let dateCreation = document.createElement("td");
          let dateNow = new Date(book.date_creation);

          if (dateNow.getUTCMonth() < 10 && dateNow.getUTCDate() < 10) {
            dateCreation.innerHTML =
              dateNow.getUTCFullYear() +
              "-0" +
              (dateNow.getUTCMonth() + 1) +
              "-0" +
              (dateNow.getUTCDate());
          } else if (dateNow.getUTCMonth() < 10) {
            dateCreation.innerHTML =
              dateNow.getUTCFullYear() +
              "-0" +
              (dateNow.getUTCMonth() + 1) +
              "-" +
              (dateNow.getUTCDate());
          } else if (dateNow.getUTCDate() < 10) {
            dateCreation.innerHTML =
              dateNow.getUTCFullYear() +
              "-" +
              (dateNow.getUTCMonth() + 1) +
              "-0" +
              (dateNow.getUTCDate());
          } else {
            dateCreation.innerHTML =
              dateNow.getUTCFullYear() +
              "-" +
              (dateNow.getUTCMonth() + 1) +
              "-" +
              (dateNow.getUTCDate());
          }

          tr.appendChild(dateCreation);

          let dateLoan = document.createElement("td");
          let dateLoanFormat = new Date(book.date_loan);
          if (
            dateLoanFormat.getUTCMonth() + 1 < 10 &&
            dateLoanFormat.getUTCDate() + 1 < 10
          ) {
            dateLoan.innerHTML =
              dateLoanFormat.getUTCFullYear() +
              "-0" +
              (dateLoanFormat.getUTCMonth()+ 1) +
              "-0" +
              (dateLoanFormat.getUTCDate());
          } else if (dateLoanFormat.getUTCMonth() + 1 < 10) {
            dateLoan.innerHTML =
              dateLoanFormat.getUTCFullYear() +
              "-0" +
              (dateLoanFormat.getUTCMonth()+ 1) +
              "-" +
              (dateLoanFormat.getUTCDate());
          } else if (dateLoanFormat.getUTCDate() + 1 < 10) {
            dateLoan.innerHTML =
              dateLoanFormat.getUTCFullYear() +
              "-" +
              (dateLoanFormat.getUTCMonth() + 1) +
              "-0" +
              (dateLoanFormat.getUTCDate());
          } else {
            dateLoan.innerHTML =
              dateLoanFormat.getUTCFullYear() +
              "-" +
              (dateLoanFormat.getUTCMonth() + 1) +
              "-" +
              (dateLoanFormat.getUTCDate());
          }
          tr.appendChild(dateLoan);
        })
        .catch((err) => console.log(err));

      let daysDifference = document.createElement("td");

      setTimeout(() => {
        fetch(url + "days/" + book.id)
          .then((res) => res.json())
          .then((data) => {
            if(data.days <= 0){
              daysDifference.style.color = "red";
              daysDifference.innerHTML += "Vencido hace " + data.days + " días";
            }
            else{
              daysDifference.innerHTML = data.days;
            }
            tr.appendChild(daysDifference);

            let buttonDelete = document.createElement("button");
            buttonDelete.setAttribute("class", "btn btn-danger");
            buttonDelete.setAttribute("onclick", "deleteBook(" + book.id + ")");
            buttonDelete.innerHTML = "Eliminar";
            tr.appendChild(buttonDelete);

            let buttonUpdate = document.createElement("button");
            buttonUpdate.setAttribute("class", "btn btn-warning ml-3");
            buttonUpdate.setAttribute("onclick", "updateBook(" + book.id + ")");
            buttonUpdate.innerHTML = "Actualizar";
            tr.appendChild(buttonUpdate);
          })
          .catch((err) => console.log(err));
      }, 50);
      app.appendChild(tr);
    });
  })
  .catch((err) => console.log(err));

function deleteBook(id) {
  fetch(url + "deleteBook/" + id, {
    method: "DELETE",
  })
    .then((res) => res.json())
    .then((data) => {
      location.reload();
    })
    .catch((err) => console.log(err));
}

function updateBook(bookId) {
  var newDateInput;
  Swal.fire({
    title: "Modificar la fecha de devolución formato YYYY-MM-DD",
    input: "text",
    inputAttributes: {
      autocapitalize: "off",
    },
    showCancelButton: true,
    confirmButtonText: "Actualizar",
    showLoaderOnConfirm: true,
    preConfirm: (newDate) => {
      return fetch(`${url}${bookId}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error(response.statusText);
          }
          newDateInput = newDate;
          return response.json();
        })
        .catch((error) => {
          Swal.showValidationMessage(`Request failed: ${error}`);
        });
    },
    allowOutsideClick: () => !Swal.isLoading(),
  }).then((result) => {
    if (result.isConfirmed) {
      var d = new Date(newDateInput);
      var newDate;

      if (d.getUTCMonth() + 1 < 10 && d.getUTCDate() + 1 < 10) {
        newDate =
          d.getUTCFullYear() +
          "-0" +
          (d.getUTCMonth() + 1) +
          "-0" +
          (d.getUTCDate() + 1);
      } else if (d.getUTCMonth() + 1 < 10) {
        newDate =
          d.getUTCFullYear() +
          "-0" +
          (d.getUTCMonth() + 1) +
          "-" +
          (d.getUTCDate() + 1);
      } else if (d.getUTCDate() + 1 < 10) {
        newDate =
          d.getUTCFullYear() +
          "-" +
          (d.getUTCMonth() + 1) +
          "-0" +
          (d.getUTCDate() + 1);
      } else {
        newDate =
          d.getUTCFullYear() +
          "-" +
          (d.getUTCMonth() + 1) +
          "-" +
          (d.getUTCDate() + 1);
      }
      
      fetch(url + "updateDate", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: bookId,
          date_loan: newDate,
        }),
      })
        .then((res) => res.json())
        .then((data) => {})
        .catch((err) => console.log(err));
      location.reload();
    }
  });
}
