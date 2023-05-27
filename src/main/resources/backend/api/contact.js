// 查询列表接口
const getContactPage = (params) => {
    return $axios({
        url: '/contact/page',
        method: 'get',
        params
    })
}

// 删除接口
const deleteContact = (ids) => {
    return $axios({
        url: '/contact',
        method: 'delete',
        params: { ids }
    })
}

const download = (params) => {
    return $axios({
        url: '/common/download',
        method: 'post',
        data: { ...params }
    })
}

// 修改接口
const editContact = (params) => {
    return $axios({
        url: '/contact',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addContact = (params) => {
    return $axios({
        url: '/contact',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryContactById = (id) => {
    return $axios({
        url: `/contact/${id}`,
        method: 'get'
    })
}
