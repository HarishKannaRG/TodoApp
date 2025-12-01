// const baseURL = "http://localhost:8080";
// const baseURL = process.env.REACT_APP_API_URL
const baseURL = import.meta.env.VITE_BACKEND_API_URL
const authenticationCheckURL = baseURL + '/api/auth/check';
const getTodosURL = baseURL + '/todo/get-todos';
const addTodoURL = baseURL + '/todo/add-todo';
const updateTodoURL = baseURL + '/todo/update-todo';
const logoutURL = baseURL + '/logout';
const deleteURL = baseURL + '/todo/delete'
export {
    baseURL,
    authenticationCheckURL,
    getTodosURL,
    addTodoURL,
    updateTodoURL,
    logoutURL,
    deleteURL
}