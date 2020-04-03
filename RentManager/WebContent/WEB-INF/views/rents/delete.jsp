<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/rents/delete">

        <!-- Main content -->
        <section class="content">
            <h1>
            Vous avez choisi de supprimer une réservation.
            </h1>
            <div>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/rents">Ok</a>
            </div>
        </section>
        <!-- /.content -->
        </form>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
