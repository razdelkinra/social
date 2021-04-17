import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { requestToFriendApi, makeFriendsApi } from "../../api/index";

import { ReactComponent as Boy1 } from "../../images/boy1.svg";
import { ReactComponent as Boy2 } from "../../images/boy2.svg";
import { ReactComponent as Boy3 } from "../../images/boy3.svg";
import { ReactComponent as Girl1 } from "../../images/girl1.svg";
import { ReactComponent as Girl2 } from "../../images/girl2.svg";
import { ReactComponent as Girl3 } from "../../images/girl3.svg";
import { ReactComponent as Add1 } from "../../images/add1.svg";

import "./UserList.css";

export const UserList = ({ getList, type }) => {
  const [list, setList] = useState([1]);
  const selfId = useSelector((state) => state.profile.data.id);

  useEffect(() => {
    getList()
      .then((resp) => setList(resp.data))
      .catch(() => setList([]));
  }, [getList]);

  const getRandomInt = (max) => Math.floor(Math.random() * max);
  const boys = {
    0: <Boy1 />,
    1: <Boy2 />,
    2: <Boy3 />,
  };
  const girl = {
    0: <Girl1 />,
    1: <Girl2 />,
    2: <Girl3 />,
  };

  const addFriendHandler = (id) => {
    if (type === "users") {
      requestToFriendApi(id, selfId);
    } else {
      makeFriendsApi(id);
    }
  };

  return (
    <div className="users">
      {list.map((user) => {
        return (
          <div className="users__item">
            <div className="users__avatar">
              {user.gender === "male"
                ? boys[getRandomInt(3)]
                : girl[getRandomInt(3)]}
              {type !== "friends" && (
                <Add1
                  className="users__add"
                  onClick={() => addFriendHandler(user.id)}
                />
              )}
            </div>
            <div className="users__name">
              {user.firstName} {user.lastName}
            </div>
          </div>
        );
      })}
    </div>
  );
};
