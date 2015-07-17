<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"
           prefix="fmt" %>
<p>${pageContext.errorData.throwable}</p>

<p>
    <c:choose>
        <c:when test="${!empty pageContext.errorData.throwable.cause}">
            : ${pageContext.errorData.throwable.cause}
        </c:when>
        <c:when test="${!empty pageContext.errorData.throwable.rootCause}">
            : ${pageContext.errorData.throwable.rootCause}
        </c:when>
    </c:choose>
</p>