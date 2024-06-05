<template>
    <div style="text-align:center; margin-bottom: 0px; margin-top: 10px;">
        <img src="~@/assets/img/catbackground.png" alt="小猫" style="width:200px;height:200px;">
    </div>
    <div class="main">
        <form @submit.prevent="submitForm">
            <h1>管理地址</h1>
            <div class="text-center">
                <span>默认地址:</span>
                <input type="text" v-model="defaultAddress" disabled>
            </div><br>
            <div v-for="(address, index) in addresses" :key="index" class="text-center address-row">
                <span>地址:</span>
                <input type="text" v-model="address" disabled>
                <button type="button" @click="removeAddress(index)" class="delete-button">删除</button>
            </div><br>
            <button type="button" @click="showAddAddressModal" class="button">添加</button>
        </form>
    </div>
    <div v-if="message" class="alert-message" id="b">
        <p>{{ message }}</p>
    </div>
    <div v-if="error" class="alert-error" id="b">
        <p>{{ error }}</p>
    </div>

    <!-- 添加地址弹窗 -->
    <div v-if="showModal" class="modal">
        <div class="modal-content">
            <span class="close" @click="closeAddAddressModal">&times;</span>
            <h2>添加新地址</h2>
            <input type="text" v-model="newAddress" placeholder="输入新地址">
            <button type="button" @click="addAddress" class="button">确认</button>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            username: '',
            userpwd: '',
            defaultAddress: '', // 默认地址
            addresses: [], // 地址列表
            showModal: false, // 控制弹窗显示
            newAddress: '', // 新地址
            message: null,
            error: null,
        };
    },
    methods: {
        async fetchAddresses() {
            // 从数据库获取地址
            try {
                const response = await fetch('/usr/get-addresses');
                const data = await response.json();
                this.defaultAddress = data.defaultAddress;
                this.addresses = data.addresses;
            } catch (error) {
                console.error("获取地址失败:", error);
                this.error = "无法获取地址，请稍后再试。";
            }
        },
        showAddAddressModal() {
            this.showModal = true;
        },
        closeAddAddressModal() {
            this.showModal = false;
            this.newAddress = '';
        },
        addAddress() {
            if (this.newAddress.trim() === '') {
                return;
            }
            this.addresses.push(this.newAddress);
            this.closeAddAddressModal();
            this.saveAddresses();
        },
        removeAddress(index) {
            this.addresses.splice(index, 1);
            this.saveAddresses();
        },
        async saveAddresses() {
            try {
                const response = await fetch('/usr/save-addresses', {
                    method: 'POST',
                    body: JSON.stringify({
                        defaultAddress: this.defaultAddress,
                        addresses: this.addresses
                    }),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                const responseData = await response.json();
                if (responseData.success) {
                    this.message = "地址保存成功";
                } else {
                    this.error = "地址保存失败，请稍后再试。";
                }
            } catch (error) {
                console.error("保存地址失败:", error);
                this.error = "地址保存失败，请稍后再试。";
            }
        }
    },
    mounted() {
        this.fetchAddresses();
    }
};
</script>

<style type="text/css" scoped>
.text-center {
    text-align: center;
}
.address-row {
    display: flex;
    justify-content: center;
    align-items: center;
}
.address-row input {
    margin-right: 10px;
}
.delete-button {
    background-color: red;
    color: white;
    border: none;
    padding: 5px 10px;
    cursor: pointer;
    border-radius: 4px;
}
.delete-button:hover {
    background-color: darkred;
}
.modal {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgb(0,0,0);
    background-color: rgba(0,0,0,0.4);
}
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
    max-width: 500px;
    text-align: center;
    border-radius: 8px;
}
.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
    cursor: pointer;
}
.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}
.modal input {
    width: 100%;
    padding: 10px;
    margin: 10px 0;
    box-sizing: border-box;
}
.button {
    border: 2px solid #333;
    padding: 8px 24px 8px 24px;
    margin-bottom: 8px;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    text-shadow: 1px 1px 1px #FFE477;
    box-shadow: 1px 1px 1px #3D3D3D;
    color: #FFFFFF;
    background: #FFA500;
}
.button:hover {
    color: #333;
    background-color: #FF8C00;
}
</style>

<style scoped>
body {
    background-image: url(~@/assets/img/beijing2.jpg);
    background-size: cover;
    margin: 0px;
    padding: 0px;
}
.alert-message, .alert-error {
    text-align: center;
    font-size: 22px;
    font-family: KaiTi;
}
.alert-error {
    color: red;
}
a {
    text-decoration: none;
    font-size: 14px;
}
span {
    font-size: 24px;
    font-family: KaiTi;
    color: black;
}
.main {
    max-width: 500px;
    margin: auto;
    border: none;
    margin-top: 80px;
    border-radius: 5px;
    background: #C0C0C0;
}
.main h1 {
    font-family: KaiTi;
    text-align: center;
    padding: 8px 0px 16px 10px;
    color: black;
    border-bottom: 1px solid #444;
}
.main input[type="text"], .main input[type="password"] {
    height: 25px;
    width: 70%;
    line-height: 15px;
    padding: 5px 0px 5px 5px;
    margin-bottom: 16px;
    margin-right: 6px;
    margin-top: 2px;
    border: none;
    border-radius: 2px;
    background: #DFDFDF;
    color: #525252;
}
button a {
    color: #FFFFFF; /* Initial color of the text */
    transition: color 0.3s ease; /* This will make the color change smoothly */
}
button:hover a {
    background: #FFA500;
    color: #333; /* Color of the text on hover */
}
</style>