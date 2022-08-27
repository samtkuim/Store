package com.sam.store.web;

import com.sam.store.pojo.Customer;
import com.sam.store.pojo.Goods;
import com.sam.store.service.CustomerService;
import com.sam.store.service.GoodsService;
import com.sam.store.service.OrdersService;
import com.sam.store.service.ServiceException;
import com.sam.store.service.impl.CustomerServiceImpl;
import com.sam.store.service.impl.GoodsServiceImpl;
import com.sam.store.service.impl.OrdersServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

//@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {


    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private CustomerService customerService = new CustomerServiceImpl();
    private GoodsService goodsService = new GoodsServiceImpl();
    private OrdersService ordersService = new OrdersServiceImpl();
    private String hotFixTest;
    private int totalPageNumber = 0;//總頁數
    private int pageSize = 10;//一頁有幾筆資料
    private int currentPage = 1;//當前頁數

    @Override
    public void init(ServletConfig config) throws ServletException {
        //ServletConfig 代表當前Servlet在web.xml中的配置信息。
        super.init(config);
        pageSize = new Integer(config.getInitParameter("pageSize"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String action = request.getParameter("action");

        if("reg".equals(action)){
            //--------註冊--------
            String userid = request.getParameter("userid");
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            String birthday = request.getParameter("birthday");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");

            List<String> errors = new ArrayList<>();

            if(userid == null || userid.equals("")){
                errors.add("帳號不能為空！");
            }
            if(name== null || name.equals("")){
                errors.add("姓名不能為空！");
            }
            if(password == null || password.equals("")){
                errors.add("密碼不能為空！");
            }
            if(password2== null || password2.equals("")){
                errors.add("再次輸入密碼不能為空！");
            }
            if(birthday == null || birthday.equals("")){
                errors.add("生日不能為空！");
            }
            if(address == null || address.equals("")){
                errors.add("地址不能為空！");
            }
            if(phone == null || phone.equals("")){
                errors.add("電話不能為空！");
            }
            if(!password.equals(password2)){
                errors.add("兩次輸入的密碼不一致！");
            }

            String pattern = "^((((19|20)(([02468][048])|([13579][26]))-02-29))|((20[0-9][0-9])|(19[0-9][0-9]))-((((0[1-9])|(1[0-2]))-((0[1-9])|(1\\d)|(2[0-8])))|((((0[13578])|(1[02]))-31)|(((0[1,3-9])|(1[0-2]))-(29|30)))))$";
            if(!Pattern.matches(pattern, birthday)){
                errors.add("生日格式錯誤！");
            }

            if(errors.size() > 0){
                //驗證失敗
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
                //若不加斜線預設就會從project下的web目錄開始找
                //而這個差異在forward上沒有差
            }else{
                //驗證成功
                Customer customer = new Customer();
                customer.setId(userid);
                customer.setName(name);
                customer.setPassword(password);
                customer.setAddress(address);
                customer.setPhone(phone);

                try {
                    Date date = dateFormat.parse(birthday);//String==>Date
                    customer.setBirthday(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    //註冊成功
                    customerService.register(customer);
                    request.getRequestDispatcher("customer_reg_success.jsp").forward(request, response);
                } catch (ServiceException e) {
                    errors.add("帳號已重複！");
                    request.setAttribute("errors", errors);
                    request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
                }
            }

        }else if("login".equals(action)){
            String userid = request.getParameter("userid");
            String password = request.getParameter("password");

            Customer customer = new Customer();
            customer.setId(userid);
            customer.setPassword(password);

            if(customerService.login(customer)){
                //登入成功
                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);
                request.getRequestDispatcher("main.jsp").forward(request, response);
            }else{
                //登入失敗
                List<String> errors = new ArrayList<>();
                errors.add("帳號或密碼輸入錯誤！");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        }else if("list".equals(action)){
            List<Goods> goodsList = goodsService.queryAll();

            if(goodsList.size() % pageSize == 0){
                totalPageNumber = goodsList.size() / pageSize;
            }else{
                totalPageNumber = (goodsList.size() / pageSize ) + 1;
            }

            request.setAttribute("totalPageNumber", totalPageNumber);
            request.setAttribute("currentPage", currentPage);
            int start = (currentPage - 1) * pageSize;
            int end = currentPage * pageSize;
            if(currentPage == totalPageNumber){//最後一頁
                end = goodsList.size();
            }
            request.setAttribute("goodsList", goodsList.subList(start, end));
            request.getRequestDispatcher("goods_list.jsp").forward(request, response);

        }else if("paging".equals(action)){
            //-----商品列表分頁-----
            String page = request.getParameter("page");

            if(page.equals("prev")){
                currentPage--;
                if(currentPage < 1){
                    currentPage = 1;
                }
            }else if(page.equals("next")){
                currentPage++;
                if(currentPage > totalPageNumber){
                    currentPage = totalPageNumber;
                }
            }else{
                currentPage = Integer.valueOf(page);//取得當前頁
            }

            int start = (currentPage - 1) * pageSize;
            int end = currentPage * pageSize;

            List<Goods> goodsList = goodsService.queryByStartEnd(start, end);

            request.setAttribute("totalPageNumber", totalPageNumber);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("goodsList", goodsList);
            request.getRequestDispatcher("goods_list.jsp").forward(request, response);

        }else if("detail".equals(action)){

            String id = request.getParameter("id");
            Goods goods = goodsService.queryDetail(new Long(id));

            request.setAttribute("goods", goods);
            request.getRequestDispatcher("goods_detail.jsp").forward(request, response);

        }else if("add".equals(action)){

            Long goodsId = new Long(request.getParameter("id"));//Long
            String goodsName = request.getParameter("name");
            Float price = new Float(request.getParameter("price"));//這裡是Float

            List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");

            if(cart == null){
                //從session中取出購物車，第一次會是null
                cart = new ArrayList<Map<String, Object>>();
                request.getSession().setAttribute("cart", cart);
            }

            //利用flag來區分
            //查看購物車存不存在此商品(存在)
            int flag = 0;
            for(Map<String, Object> item : cart){
                Long goodsId2 =(Long)item.get("goodsid");//Long
                if(goodsId2.equals(goodsId)){//購物車已存在此商品
                    Integer quantity = (Integer) item.get("quantity");
                    quantity++;//則直接把數量+1
                    item.put("quantity", quantity);//更新數量

                    flag++;
                }
            }

            //查看購物車存不存在此商品(不存在)
            if(flag == 0){

                Map<String, Object> item = new HashMap<>();
                //item 結構是Map [商品id，商品名稱，商品價格，商品數量]

                item.put("goodsid", goodsId);
                item.put("goodsname", goodsName);
                item.put("price", price);
                item.put("quantity", 1);//第一次添加到購物車，因此數量為1

                //把商品添加到購物車
                cart.add(item);
            }

            System.out.println(cart);

            //因為有兩處都有加入購物車，但要跳轉回自己的，因此要判斷
            String pagename = request.getParameter("pagename");

            if(pagename.equals("list")){
                //記得插入分頁
                int start = (currentPage - 1) * pageSize;
                int end = currentPage * pageSize;

                List<Goods> goodsList = goodsService.queryByStartEnd(start, end);

                request.setAttribute("totalPageNumber", totalPageNumber);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("goodsList", goodsList);

                request.getRequestDispatcher("goods_list.jsp").forward(request, response);

            }else if(pagename.equals("detail")){
                String id = request.getParameter("id");
                Goods goods = goodsService.queryDetail(new Long(id));

                request.setAttribute("goods", goods);
                request.getRequestDispatcher("goods_detail.jsp").forward(request, response);

            }
        }else if("cart".equals(action)){
            //從session中取出購物車
            List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");

            double total = 0.0;//總金額

            if(cart != null){
                for(Map<String,Object> item : cart){
//                    Long goodsid = (Long) item.get("goodsid");
                    Integer quantity = (Integer) item.get("quantity");
                    Float price = (Float) item.get("price");
                    double subTotal = quantity * price;//商品數量*金額
                    total += subTotal;
                }
            }

            request.setAttribute("total", total);
            request.getRequestDispatcher("cart.jsp").forward(request, response);

        }else if("sub_ord".equals(action)){
            //從session中取出購物車
            List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");

            for(Map<String, Object> item : cart){
                Long goodsid = (Long) item.get("goodsid");
                String strquantity = request.getParameter("quantity_" + goodsid);
                int quantity = 0;
                try{
                    //有可能用戶端那邊輸入非法字元，如果被提交一律以0來看待
                    quantity = new Integer(strquantity);
                }catch (Exception e){

                }
                item.put("quantity", quantity);
            }
            String ordersid = ordersService.submitOrders(cart);
            request.setAttribute("ordersid", ordersid);
            request.getRequestDispatcher("order_finish.jsp").forward(request, response);
            //提交訂單後清空購物車
            request.getSession().removeAttribute("cart");

        }else if("main".equals(action)){
            request.getRequestDispatcher("main.jsp").forward(request, response);

        }else if("logout".equals(action)){

            request.getSession().removeAttribute("cart");
            request.getSession().removeAttribute("customer");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        }else if("reg_init".equals(action)){
            request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
        }
    }


}
