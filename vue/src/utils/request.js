import axios from 'axios'

//创建一个axios对象出来
const request = axios.create({
    baseURL: 'http://localhost:8082',
    timeout: 5000
})
// request 拦截器
//可以自请求发送前对请求做一些处理
// 比如统-加token，对请求参数统一加密
request.interceptors.request.use( config => {
    config.headers['content-Type'] = 'application/json;charset=utf-8';

    //config.headers['token']=user.token;// 设置请求头
    return config
},error => {
    return Promise.reject(error)
});

// response 拦截器
//可以在接口响应后统一处理结果
request.interceptors.response.use(
    response =>{
        //response.data即为后端返回的Result
        let res =response.data;
        if(typeof res ==='string'){
            res = res ?JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error)
        return Promise.reject(error)
    }
)

export default request