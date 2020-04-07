<template>
  <div class="headImgInput">
    <el-upload
      class="avatar-uploader"
      :action="baseURL+'/api/mall/user/headImgUpload'"
      :show-file-list="false"
      :http-request="httpUpload"
      :on-success="handleAvatarSuccess"
      :on-change="fileChange"
      :before-upload="beforeAvatarUpload">
      <img v-if="headImageUrl" :src="headImageUrl" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>

  </div>
</template>

<script>
  import axios from '../config/axios-client';
  export default {
    name: 'HeadImgInput',
    data() {
      return {
        file: undefined,
        headImageUrl: '',
        headImageUrlUpload: '',
        baseURL:''
      }
    },
    mounted(){
      this.baseURL = this.$baseURL
    },
    methods: {
      httpUpload() {
        const fd = new FormData()
        fd.append('file', this.file.raw)
        const config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }

        console.log(this.file)
        console.log(this.file.raw)

        axios.post(this.baseURL+'/api/mall/user/headImgUpload', fd, config)
          .then((result)=>{
            if(result.status===200){
              return result.data;
            }else{
              alert('检查请求')
            }
          })
          .then((json)=>{
            if(json.code===0){
              this.headImageUrl = URL.createObjectURL(this.file.raw)

              this.headImageUrlUpload = json.data
              this.$emit('headImageUrl',this.headImageUrlUpload);
            }else{
              alert(json.message)
            }
          })
          .catch((e)=>{
            alert(e.toString())
          })
      },
      fileChange(file) {
        this.file = file
      },
      handleAvatarSuccess(res, file) {
        // 方法已经废弃
        // this.imageUrl = URL.createObjectURL(file.raw)
        // this.imageUrlUpload = res.data
        // this.$emit('imageUrl',this.imageUrlUpload);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      }
    }
  }
</script>

<style>
  .headImgInput{
    margin-left: 5px;
    margin-top: 10px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 70px;
    height: 70px;
    line-height: 70px;
    text-align: center;
  }
  .avatar {
    width: 70px;
    height: 70px;
    display: block;
  }
</style>

