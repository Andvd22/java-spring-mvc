<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
    <meta name="author" content="Hỏi Dân IT" />
    <title>Dashboard - Hỏi Dân IT</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
   <!-- xem truoc anh -->
                   <script>
                       $(document).ready(() => {
                           const productFile = $("#productFile");
                           productFile.change(function (e) {
                               const imgURL = URL.createObjectURL(e.target.files[0]);
                               $("#imgProductPreview").attr("src", imgURL);
                               $("#imgProductPreview").css({ "display": "block" });
                           });
                       });
                   </script>
   
</head>

<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp"/>
    <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp"/>
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Manage Users</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item active"><a href="/admin">Dashboard</a></li>
                        <li class="breadcrumb-item active">Users</li>
                    </ol>
                    <div class="container mt-5">
                      <div class="row">
                          <div class="col-md-6 col-12 mx-auto">
                              <h3>Create a product</h3>
                              <hr />
                              <form:form method="post" action="/admin/product/create" modelAttribute="newProduct" class="row" enctype="multipart/form-data">

                                    <div class="mb-3 col-12 col-md-6">
                                      <c:set var="errorName">
                                        <form:errors path="name" cssClass="invalid-feedback"/>
                                      </c:set>
                                      <label class="form-label">Name:</label>
                                      <form:input type="text" class="form-control ${not empty errorName ? 'is-invalid' : ''}" path="name"/> 
                                      ${errorName}
                                    </div>

                                    <div class="mb-3 col-12 col-md-6">
                                      <c:set var="errorPrice">
                                        <form:errors path="price" cssClass="invalid-feedback"/>
                                      </c:set>
                                      <label class="form-label">Price:</label>
                                      <form:input type="number" class="form-control ${not empty errorPrice ?'is-invalid':''}" path="price"/>
                                      ${errorPrice}
                                    </div>

                                    <div class="mb-3 col-12">
                                      <c:set var="errorDetailDesc">
                                        <form:errors path="detailDesc" cssClass="invalid-feedback"/>
                                      </c:set>
                                        <label class="form-label">Detail description:</label>
                                        <form:input type="text" class="form-control ${not empty errorDetailDesc ?'is-invalid':''}" path="detailDesc"/>
                                      ${errorDetailDesc}
                                      </div>
                 
                                    <div class="mb-3 col-12 col-md-6">
                                      <c:set var="errorShortDesc">
                                        <form:errors path="shortDesc" cssClass="invalid-feedback"/>
                                      </c:set>
                                      <label class="form-label">Short description:</label>
                                      <form:input type="text" class="form-control ${not empty errorShortDesc ?'is-invalid':''}" path="shortDesc"/>
                                      ${errorShortDesc}
                                    </div>

                                    <div class="mb-3 col-12 col-md-6">
                                      <c:set var="errorQuantity">
                                        <form:errors path="quantity" cssClass="invalid-feedback"/>
                                      </c:set>
                                      <label class="form-label">Quantity:</label>
                                      <form:input type="number" class="form-control ${not empty errorQuantity ?'is-invalid':''}" path="quantity"/>
                                      ${errorQuantity}
                                    </div>
                                    
                                    

       
                                  <div class="mb-3 col-12 col-md-6">
                                      <label class="form-label">Factory:</label>
                                      <form:select class="form-select" path="factory">
                                        <form:option value="APPLE">Apple(Macbook)</form:option>
                                        <form:option value="DELL">Dell</form:option>
                                      </form:select>
                                  </div>
                                  <div class="mb-3 col-12 col-md-6">
                                    <label class="form-label">Target:</label>
                                    <form:select class="form-select" path="target">
                                      <form:option value="GAMING">Gaming</form:option>
                                      <form:option value="ABCXSDS">abcxsds</form:option>
                                    </form:select>
                                </div>


                                  <div class="mb-3 col-12 COD-MD-6">
                                      <label for="avatarFile" class="form-label">Image:</label>
                                      <input class="form-control" type="file" id="productFile" accept=".png, .jpg, .jpeg" name="hoidanitFile"/>
                                  </div>
                                  <div class="col-12 mb-3">
                                    <img style="max-height: 250px; display: none;" alt="imgProduct preview" id="imgProductPreview">
                                  </div>
                                  <div class="col-12 mb-5"><button type="submit" class="btn btn-primary">Create</button></div>
                              </form:form>
                          </div>
                      </div>
                    </div>
                </div>
            </main>
            <jsp:include page="../layout/footer.jsp"/>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
</body>

</html>