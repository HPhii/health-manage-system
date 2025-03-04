import { ConfigProvider, Menu } from "antd";
import SimpleBar from "simplebar-react";
import "simplebar-react/dist/simplebar.min.css";
import { useLocation, useNavigate } from "react-router-dom";
import { useCallback, useMemo } from "react";
import MenuAdminConfig from "~/layouts/AdminLayout/menuAdminConfig";


const MenuCustom = ({ isMobile, onClose, theme = "light", ...props }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const menuItems = MenuAdminConfig();

  const findItemByPath = useCallback((items, path) => {
    for (const item of items) {
      if (item.path && item.path.includes(path)) {
        return item;
      }
      if (item.children) {
        const child = findItemByPath(item.children, path);
        if (child) return child;
      }
    }
  }, []);

  const selectedKeys = useMemo(() => {
    const item = findItemByPath(menuItems, location.pathname);
    return item ? [item.key] : [];
  }, [location.pathname, findItemByPath]);

  const handleMenuClick = useCallback(
    (e) => {
      if (isMobile) {
        onClose();
      }
      navigate(e.item.props.path);
    },
    [isMobile, onClose, navigate]
  );

  return (
    <ConfigProvider
      theme={{
        token: {
          controlHeight: 32,
        },
      }}
    >
      <SimpleBar style={{ maxHeight: "calc(100vh - 80px)" }}>
        <Menu
          theme={theme}
          selectedKeys={selectedKeys}
          items={menuItems}
          rootClassName="!border-none"
          onClick={handleMenuClick}
          {...props}
        ></Menu>
      </SimpleBar>
    </ConfigProvider>
  );
};


export default MenuCustom;
