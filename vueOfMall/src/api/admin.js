import axios from '../config/axios-admin';

//管理员登录
export function login(data){
	const res = axios.post('/api/admin/admin/login',data);
  // const res = axios.get('/api/admin/login');
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//获取所有管理员信息
export function getAllAdmins(){
  const res = axios.get('/api/admin/admin/allAdmins');
  return new Promise((resolve,reject)=>{
    res
      .then((result)=>{
        if(result.status===200){
          return result.data;
        }else{
          reject(result.status)
        }
      })
      .then((json)=>{
        if(json.code===0){
          resolve(json.data);
        }else{
          reject(json.message);
        }
      })
      .catch((e)=>{
        reject(e.toString())
      })
  })
}
//获取所有用户信息
export function getAllUser(){
	const res = axios.get('/api/admin/user/allUser');
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}
//删除指定管理员
export function deleteAdmins(id){
  const res = axios.get('/api/admin/admin/deleteAdmin?id='+id);
  return new Promise((resolve,reject)=>{
    res
      .then((result)=>{
        if(result.status===200){
          return result.data;
        }else{
          reject(result.status)
        }
      })
      .then((json)=>{
        if(json.code===0){
          resolve();
        }else{
          reject(json.message);
        }
      })
      .catch((e)=>{
        reject(e.toString())
      })
  })
}

//删除指定用户
export function deleteUser(id){
	const res = axios.get('/api/admin/user/deleteUser?id='+id);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//查找指定管理员
export function getSearchAdmins(data){
  const res = axios.post('/api/admin/admin/getSearchAdmin',data);
  // const res = axios.get('/api/admin/login');
  return new Promise((resolve,reject)=>{
    res
      .then((result)=>{
        if(result.status===200){
          return result.data;
        }else{
          reject(result.status)
        }
      })
      .then((json)=>{
        if(json.code===0){
          resolve(json.data);
        }else{
          reject(json.message);
        }
      })
      .catch((e)=>{
        reject(e.toString())
      })
  })
}
//搜索用户
export function getSearchUser(val){
	const res = axios.get('/api/admin/user/searchUser?word='+val);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//更改密码
export function changePwd(data){
	const res = axios.post('/api/admin/admin/changePwd',data);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//得到未回复的信息
export function getNoReplyMsg(){
	const res = axios.get('/api/admin/reply/noReplyMsg');
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//得到已回复的信息
export function getRepliedMsg(){
	const res = axios.get('/api/admin/reply/repliedMsg');
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//得到已回复的信息
export function reply(data){
	const res = axios.post('/api/admin/reply/reply',data);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}
//得到分页订单
export function getOrdersByPage(data){
  const res = axios.post('/api/admin/order/ordersByPage',data);
  return new Promise((resolve,reject)=>{
    res
      .then((result)=>{
        if(result.status===200){
          return result.data;
        }else{
          reject(result.status)
        }
      })
      .then((json)=>{
        if(json.code===0){
          resolve(json.data);
        }else{
          reject(json.message);
        }
      })
      .catch((e)=>{
        reject(e.toString())
      })
  })
}
//得到订单
export function getOrders(state){
	const res = axios.get('/api/admin/orders?state='+state);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//得到订单
export function getAOrder(id){
	const res = axios.get('/api/admin/order/order?id='+id);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//修改订单
export function changeOrder(data){
	const res = axios.post('/api/admin/order/changeOrder',data);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//删除指定用户
export function deleteOrder(id){
	const res = axios.get('/api/admin/order/deleteOrder?id='+id);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//得到商品
export function getGoods(id){
	const res = axios.get('/api/admin/good/getGoodsByType?typeId='+id);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//得到类目
export function getTypes(){
	const res = axios.get('/api/admin/good/getTypes');
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//增加类目
export function addType(data){
	const res = axios.post('/api/admin/good/addType',data);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//修改管理员
export function updateAdminss(data){
  const res = axios.post('/api/admin/admin/updateAdmin',data);
  return new Promise((resolve,reject)=>{
    res
      .then((result)=>{
        if(result.status===200){
          return result.data;
        }else{
          reject(result.status)
        }
      })
      .then((json)=>{
        if(json.code===0){
          resolve();
        }else{
          reject(json.message);
        }
      })
      .catch((e)=>{
        reject(e.toString())
      })
  })
}

//增加管理员
export function addAdminss(data){
  const res = axios.post('/api/admin/admin/addAdmin',data);
  return new Promise((resolve,reject)=>{
    res
      .then((result)=>{
        if(result.status===200){
          return result.data;
        }else{
          reject(result.status)
        }
      })
      .then((json)=>{
        if(json.code===0){
          resolve();
        }else{
          reject(json.message);
        }
      })
      .catch((e)=>{
        reject(e.toString())
      })
  })
}

//得到用户
export function getAdminsInfo(id){
  const res = axios.get('/api/admin/admin/getAdminInfo?id='+id);
  return new Promise((resolve,reject)=>{
    res
      .then((result)=>{
        if(result.status===200){
          return result.data;
        }else{
          reject(result.status)
        }
      })
      .then((json)=>{
        if(json.code===0){
          resolve(json.data);
        }else{
          reject(json.message);
        }
      })
      .catch((e)=>{
        reject(e.toString())
      })
  })
}


//得到商品信息
export function getGoodsInfo(id){
	const res = axios.get('/api/admin/good/getGoodsInfo?id='+id);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//增加商品
export function addGoods(data){
	const res = axios.post('/api/admin/good/addGood',data);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//增加规格
export function addSpec(data){
	const res = axios.post('/api/admin/good/addSpec',data);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve(json.data);
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//删除规格
export function deleteSpec(data){
	const res = axios.post('/api/admin/good/deleteSpec',data);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//更新商品信息
export function updateGoods(data){
	const res = axios.post('/api/admin/good/updateGood',data);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//删除指定商品
export function deleteGoods(id){
	const res = axios.get('/api/admin/good/deleteGood?id='+id);
	return new Promise((resolve,reject)=>{
		res
		.then((result)=>{
	        if(result.status===200){
	        	return result.data;
	        }else{
	        	reject(result.status)
	        }
	    })
	    .then((json)=>{
    		if(json.code===0){
                resolve();
            }else{
                reject(json.message);
            }
    	})
	    .catch((e)=>{
	    	reject(e.toString())
	    })
	})
}

//删除指定分类
export function deleteType(id){
  const res = axios.get('/api/admin/good/deleteType?typeId='+id);
  return new Promise((resolve,reject)=>{
    res
      .then((result)=>{
        if(result.status===200){
          return result.data;
        }else{
          reject(result.status)
        }
      })
      .then((json)=>{
        if(json.code===0){
          resolve();
        }else{
          reject(json.message);
        }
      })
      .catch((e)=>{
        reject(e.toString())
      })
  })
}

// 退出登录
export function logoutAdmin(data){
  const res = axios.post('/api/admin/admin/logoutAdmin',data);
  return new Promise((resolve,reject)=>{
    res
      .then((result)=>{
        if(result.status===200){
          return result.data;
        }else{
          reject(result.status)
        }
      })
      .then((json)=>{
        if(json.code===0){
          resolve();
        }else{
          reject(json.message);
        }
      })
      .catch((e)=>{
        reject(e.toString())
      })
  })
}

