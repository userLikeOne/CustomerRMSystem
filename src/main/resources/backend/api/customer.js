// 查询列表接口
const getCustomerPage = (params) => {
  return $axios({
    url: '/contact/page',
    method: 'get',
    params
  })
}

// 删除接口
const deleteCustomer = (ids) => {
  return $axios({
    url: '/customer',
    method: 'delete',
    params: { ids }
  })
}

// 修改接口
const editCustomer = (params) => {
  return $axios({
    url: '/customer',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
const addCustomer = (params) => {
  return $axios({
    url: '/customer',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
const queryCustomerById = (id) => {
  return $axios({
    url: `/customer/${id}`,
    method: 'get'
  })
}

const download = (params) => {
  return $axios({
    url: '/common/download',
    method: 'post',
    responseType: 'blob',
    data: { ...params }
  })
}
